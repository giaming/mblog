package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author giaming
 * @date 2022/2/12 - 18:08
 * @detail
 */

@Target(ElementType.METHOD)  //注解用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时使用
public @interface VisitLogger {
    String behavior() default "" ;
    String content() default "";
}
