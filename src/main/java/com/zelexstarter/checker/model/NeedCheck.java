package com.zelexstarter.checker.model;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedCheck {

    // 校验方式 （枚举）
    CheckIn value() default CheckIn.PHONE;

    // 校验参数
    String arg() default "";

    // 错误信息
    String msg() default "";
}
