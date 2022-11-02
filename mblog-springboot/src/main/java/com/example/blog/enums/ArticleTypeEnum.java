package com.example.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: ljm
 * @Date: 2022/8/16 13:17
 * @Description: <p>
 * 文章类型枚举
 * </p>
 */
@Getter
@AllArgsConstructor
public enum ArticleTypeEnum {
    /**
     * 原始
     */
    ORIGINAL(1,"原创"),
    /**
     * 转载
     */
    REPRINTED(2,"转载"),

    /**
     * 翻译
     */
    TRANSLATION(3,"翻译");

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;
}
