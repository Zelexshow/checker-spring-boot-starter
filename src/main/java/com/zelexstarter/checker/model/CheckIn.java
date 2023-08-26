package com.zelexstarter.checker.model;

import com.zelexstarter.checker.util.CheckUtil;

import java.util.function.Function;

public enum CheckIn {

    // 邮箱校验方式
    PHONE("参数应为PHONE格式", CheckUtil::isPhone);

    public String msg;

    // 函数式接口 Object为传入参数类型，Boolean为返回类型
    public Function<Object, Boolean> function;

    CheckIn(String msg, Function<Object, Boolean> function) {
        this.msg = msg;
        this.function = function;
    }

}
