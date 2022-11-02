package com.example.blog.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ljm
 * @Date: 2022/8/16 13:07
 * @Description: <p>
 *
 * </p>
 */
public interface ArticleImportStrategy {
    /**
     * 导入文章
     */
    void importArticles(MultipartFile file);
}
