package com.example.blog.constant;

/**
 * @Author: ljm
 * @Date: 2022/8/16 12:52
 * @Description: <p>
 *  Hexo文章常量
 *  Hexo结构 --- title: xxx date: yyyy-MM-dd HH:mm:ss categories: [a, b, c] tags: [d, e, f] --- 正文
 * </p>
 */
public class HexoConst {
    /**
     * 标题
     */
    public static final String TITLE_PREFIX = "title:";

    /**
     * 日期
     */
    public static final String DATE_PREFIX = "date:";

    /**
     * 分类
     */
    public static final String CATEGORIES_PREFIX = "categories:";

    /**
     * 标签
     */
    public static final String TAGS_PREFIX = "tags:";

    /**
     * 分隔符
     */
    public static final String DELIMITER = "---";

    /**
     * 前缀
     */
    public static final String PREFIX = "-";

    /**
     * 正常标记
     */
    public static final Integer NORMAL_FLAG = 0;

    /**
     * 分类标记
     */
    public static final Integer CATEGORY_FLAG = 1;

    /**
     * 标签标记
     */
    public static final Integer TAG_FLAG = 2;

    /**
     * 换行符
     */
    public static final String NEW_LINE = "\n";
}
