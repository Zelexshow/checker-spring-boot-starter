package com.zelex.starter.checker.util;

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
            //带区号
            String regExWithAreaNum = "^[0][1-9]{2,3}-[0-9]{5,10}$";
            // 没带区号
            String regExWithoutAreaNum = "^[1-9]{1}[0-9]{5,8}$";
            Pattern p1 = Pattern.compile(regExWithAreaNum);
            Pattern p2 = Pattern.compile(regExWithoutAreaNum);
            Matcher m = str.length() > 9 ? p1.matcher(str) : p2.matcher(str);
            return m.matches() ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
}
