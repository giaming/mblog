package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.lang.vo.BlogInfo;
import com.example.entity.Blog;

import java.util.List;

/**
 * 服务接口
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
 */
public interface BlogService extends IService<Blog> {

    /**
     * 通过分类名查找属于该分类的博客list
     * @param categoryName  分类名
     * @return 博客list
     */
    List<BlogInfo> getBlogInfoListByCategoryName(String categoryName);
}
