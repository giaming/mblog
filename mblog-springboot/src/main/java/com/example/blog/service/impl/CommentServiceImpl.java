package com.example.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.blog.constant.CommonConst;
import com.example.blog.constant.MQPrefixConst;
import com.example.blog.constant.RedisPrefixConst;
import com.example.blog.dao.ArticleDao;
import com.example.blog.dao.CommentDao;
import com.example.blog.dao.TalkDao;
import com.example.blog.dao.UserInfoDao;
import com.example.blog.entity.Comment;
import com.example.blog.enums.CommentTypeEnum;
import com.example.blog.service.BlogInfoService;
import com.example.blog.service.CommentService;
import com.example.blog.service.RedisService;
import com.example.blog.util.HTMLUtils;
import com.example.blog.util.PageUtils;
import com.example.blog.util.UserUtils;
import com.example.blog.dto.*;
import com.example.blog.vo.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 评论服务
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private TalkDao talkDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 网站网址
     */
    @Value("${website.url}")
    private String websiteUrl;

    @Override
    public PageResult<CommentDTO> listComments(CommentVO commentVO) {
        // 查询评论量
        Integer commentCount = commentDao.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(CommentTypeEnum.ARTICLE.getType().equals(commentVO.getType()), Comment::getArticleId, commentVO.getArticleId())
                .eq(CommentTypeEnum.TALK.getType().equals(commentVO.getType()), Comment::getTalkId, commentVO.getTalkId())
                .eq(CommentTypeEnum.LINK.getType().equals(commentVO.getType()), Comment::getType, commentVO.getType())
                .isNull(Comment::getParentId)
                .eq(Comment::getIsReview, CommonConst.TRUE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        // 分页查询评论集合
        List<CommentDTO> commentDTOList = commentDao.listComments(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOList)) {
            return new PageResult<>();
        }
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(RedisPrefixConst.COMMENT_LIKE_COUNT);
        // 提取评论id集合
        List<Integer> commentIdList = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = commentDao.listReplies(commentIdList);
        // 封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        Map<Integer, Integer> replyCountMap = commentDao.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageResult<>(commentDTOList, commentCount);
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId) {
        // 转换页码查询评论下的回复
        List<ReplyDTO> replyDTOList = commentDao.listRepliesByCommentId(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentId);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(RedisPrefixConst.COMMENT_LIKE_COUNT);
        // 封装点赞数据
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        return replyDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveComment(CommentVO commentVO) {
        // 判断是否需要审核
        WebsiteConfigVO websiteConfig = blogInfoService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        // 过滤标签
        commentVO.setCommentContent(HTMLUtils.deleteTag(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(UserUtils.getLoginUser().getUserInfoId())
                .replyUserId(commentVO.getReplyUserId())
                .articleId(commentVO.getArticleId())
                .talkId(commentVO.getTalkId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isReview == CommonConst.TRUE ? CommonConst.FALSE : CommonConst.TRUE)
                .build();
        commentDao.insert(comment);
        // 判断是否开启邮箱通知,通知用户
        CompletableFuture.runAsync(() -> {
            if (websiteConfig.getIsEmailNotice().equals(CommonConst.TRUE)) {
                notice(comment);
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCommentLike(Integer commentId) {
        // 判断是否点赞
        String commentLikeKey = RedisPrefixConst.COMMENT_USER_LIKE + UserUtils.getLoginUser().getUserInfoId();
        if (redisService.sIsMember(commentLikeKey, commentId)) {
            // 点过赞则删除评论id
            redisService.sRemove(commentLikeKey, commentId);
            // 评论点赞量-1
            redisService.hDecr(RedisPrefixConst.COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisService.sAdd(commentLikeKey, commentId);
            // 评论点赞量+1
            redisService.hIncr(RedisPrefixConst.COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        // 修改评论审核状态
        List<Comment> commentList = reviewVO.getIdList().stream().map(item -> Comment.builder()
                        .id(item)
                        .isReview(reviewVO.getIsReview())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }

    @Override
    public PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO condition) {
        // 统计后台评论量
        Integer count = commentDao.countCommentDTO(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台评论集合
        List<CommentBackDTO> commentBackDTOList = commentDao.listCommentBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(commentBackDTOList, count);
    }

    /**
     * 通知评论用户
     *
     * @param comment 评论信息
     */
    public void notice(Comment comment) {
        // 查询回复用户邮箱号
        Integer userId = CommonConst.BLOGGER_ID;
        String id = "";
        switch (Objects.requireNonNull(CommentTypeEnum.getCommentEnum(comment.getType()))) {
            case ARTICLE:
                userId = articleDao.selectById(comment.getArticleId()).getUserId();
                id = comment.getArticleId().toString();
                break;
            case TALK:
                userId = talkDao.selectById(comment.getTalkId()).getUserId();
                id = comment.getTalkId().toString();
                break;
            default:
                break;
        }
        if (Objects.nonNull(comment.getReplyUserId())) {
            userId = comment.getReplyUserId();
        }
        String email = userInfoDao.selectById(userId).getEmail();
        if (StringUtils.isNotBlank(email)) {
            // 发送消息
            EmailDTO emailDTO = new EmailDTO();
            if (comment.getIsReview().equals(CommonConst.TRUE)) {
                // 评论提醒
                emailDTO.setEmail(email);
                emailDTO.setSubject("评论提醒");
                // 获取评论路径
                String url = websiteUrl + CommentTypeEnum.getCommentPath(comment.getType()) + id;
                emailDTO.setContent("您收到了一条新的回复，请前往" + url + "\n页面查看");
            } else {
                // 管理员审核提醒
                String adminEmail = userInfoDao.selectById(CommonConst.BLOGGER_ID).getEmail();
                emailDTO.setEmail(adminEmail);
                emailDTO.setSubject("审核提醒");
                emailDTO.setContent("您收到了一条新的回复，请前往后台管理页面审核");
            }
            rabbitTemplate.convertAndSend(MQPrefixConst.EMAIL_EXCHANGE, "*",
                    new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        }
    }

}
