package com.forfries.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *这个注解加在方法上
 * 自动获取方法的long id字段
 * 获取方法的mapper对象，搜索id字段对应的行的cinemaId是否为token中id
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CinemaIdCheck {
}
