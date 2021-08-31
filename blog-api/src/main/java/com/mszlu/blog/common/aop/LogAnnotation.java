package com.mszlu.blog.common.aop;

import java.lang.annotation.*;

/**
 * 打印日志的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}