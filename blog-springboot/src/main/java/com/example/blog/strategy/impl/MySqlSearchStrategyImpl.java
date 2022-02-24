package com.example.blog.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.blog.constant.CommonConst;
import com.example.blog.dao.ArticleDao;
import com.example.blog.dto.ArticleSearchDTO;
import com.example.blog.entity.Article;
import com.example.blog.enums.ArticleStatusEnum;
import com.example.blog.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * mysql搜索策略
 */
@Service("mySqlSearchStrategyImpl")
public class MySqlSearchStrategyImpl implements SearchStrategy {
    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) {
        // 判空
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        // 搜索文章
        List<Article> articleList = articleDao.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, CommonConst.FALSE)
                .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus())
                .and(i -> i.like(Article::getArticleTitle, keywords)
                        .or()
                        .like(Article::getArticleContent, keywords)));
        // 高亮处理
        return articleList.stream().map(item -> {
            // 获取关键词第一次出现的位置
            String articleContent = item.getArticleContent();
            int index = item.getArticleContent().indexOf(keywords);
            if (index != -1) {
                // 获取关键词前面的文字
                int preIndex = index > 25 ? index - 25 : 0;
                String preText = item.getArticleContent().substring(preIndex, index);
                // 获取关键词到后面的文字
                int last = index + keywords.length();
                int postLength = item.getArticleContent().length() - last;
                int postIndex = postLength > 175 ? last + 175 : last + postLength;
                String postText = item.getArticleContent().substring(index, postIndex);
                // 文章内容高亮
                articleContent = (preText + postText).replaceAll(keywords, CommonConst.PRE_TAG + keywords + CommonConst.POST_TAG);
            }
            // 文章标题高亮
            String articleTitle = item.getArticleTitle().replaceAll(keywords, CommonConst.PRE_TAG + keywords + CommonConst.POST_TAG);
            return ArticleSearchDTO.builder()
                    .id(item.getId())
                    .articleTitle(articleTitle)
                    .articleContent(articleContent)
                    .build();
        }).collect(Collectors.toList());
    }

}
