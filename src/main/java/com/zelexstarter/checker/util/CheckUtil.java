package com.zelexstarter.checker.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

    /**
     * 使用正则表达式判断是否是手机号
     */
    public static Boolean isPhone(Object value) {
        if(value == null) {
            return Boolean.FALSE;
        }
        if(value instanceof String str && ((String) value).length() > 0) {

            // 没带区号
            String regExWithoutAreaNum = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
            Matcher m = Pattern.compile(regExWithoutAreaNum).matcher(str);
            return m.matches() ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    public static void main(String[] args) {
//        String phone = "15863929788";
        String phone = "11573929788";
        System.out.println(isPhone(phone));

    }
}
