package com.example.blog.annotation;

import java.lang.annotation.*;

/**
 * @Author: ljm
 * @Date: 2022/8/16 11:37
 * @Description: <p>
 *
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    /**
     * 单位时间秒
     * @return
     */
    int seconds();

    /**
     * 单位时间最大请求次数
     * @return
     */
    int maxCount();
}
