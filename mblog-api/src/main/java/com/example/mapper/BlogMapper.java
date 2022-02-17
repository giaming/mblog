package com.example.mapper;

import com.example.common.lang.vo.BlogInfo;
import com.example.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (blog)数据Mapper
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 根据分类查询博客
     */
    List<BlogInfo> getBlogByTypeName(String typeName);
}
