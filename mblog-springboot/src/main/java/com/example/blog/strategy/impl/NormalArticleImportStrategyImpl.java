package com.example.blog.strategy.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.example.blog.exception.BizException;
import com.example.blog.service.ArticleService;
import com.example.blog.strategy.ArticleImportStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @Author: ljm
 * @Date: 2022/8/16 13:37
 * @Description: <p>
 * 普通文章导入策略
 * </p>
 */
@Slf4j
@Service("normalArticleImportStrategyImpl")
public class NormalArticleImportStrategyImpl implements ArticleImportStrategy {
    @Resource
    private ArticleService articleService;
    @Override
    public void importArticles(MultipartFile file) {
        // 获取文件名作为文章标题
        String articleTitle = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0];
        // 获取文章内容
        StringBuilder articleContent = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (reader.ready()) {
                articleContent.append((char) reader.read());
            }
        } catch (IOException e) {
            log.error(StrUtil.format("导入文章失败, 堆栈:{}", ExceptionUtil.stacktraceToString(e)));
            throw new BizException("导入文章失败");
        }
    }
}
