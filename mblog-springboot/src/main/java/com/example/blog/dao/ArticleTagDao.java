package com.example.blog.dao;

import com.example.blog.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * 文章标签
 */
@Repository
public interface ArticleTagDao extends BaseMapper<ArticleTag> {

}
