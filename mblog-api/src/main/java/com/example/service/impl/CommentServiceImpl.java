package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.lang.vo.PageComment;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务接口实现
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
//    private final CommentMapper commentMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<PageComment> getPageCommentListByDesc(Long blogId, Long parentCommentId) {
        return commentMapper.getPageCommentListByPageAndParentCommentIdByDesc(blogId, parentCommentId);
    }

    @Override
    public List<PageComment> getPageCommentList(Long blogId, Long parentCommentId) {
        return commentMapper.getPageCommentListByPageAndParentCommentId(blogId, parentCommentId);
    }
}