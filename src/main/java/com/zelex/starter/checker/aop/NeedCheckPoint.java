package com.zelex.starter.checker.aop;

import com.zelex.starter.checker.model.CheckIn;
import com.zelex.starter.checker.model.NeedCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@Aspect
public class NeedCheckPoint {

    // 记录日志
    private final Logger log = LoggerFactory.getLogger(NeedCheckPoint.class);

    /**
     * 自定义切入点
     * 切入点说明：这样指定的切入点是任何一个执行的方法有一个 @NeedCheck 注解的连接点（这里连接点可以看是方法）
     */
    @Pointcut("@annotation(com.zelex.starter.checker.model.NeedCheck)")
    private void needCheckPoint() {}

    /**
     * 定义环绕通知
     */
    @Around("needCheckPoint()")
    public Object needCheck(ProceedingJoinPoint jp) throws Throwable {
        // 获取被修饰的方法信息
        Method method = getMethod(jp);
        // 获取方法的所有参数值
        Object[] args = jp.getArgs();
        // 获取方法的所有参数名
        String[] paramNames = getParamName(jp);

        // 获取注解信息
        NeedCheck annotation = method.getAnnotation(NeedCheck.class);
        // 获取需要校验的参数名
        String argName = annotation.arg();
        // 获取需要的校验方式枚举类
        CheckIn value = annotation.value();
        // 获取需要返回的报错信息
        String msg = annotation.msg();

        // 判断是否未配置msg,未配置则直接使用枚举类的固定提示
        if ("".equals(msg)) msg = value.msg;

        // 获取需要校验的参数值
        Object argValue = getArgValue(argName, args, paramNames);

        // 记录日志
        log.info("校验方法：{} 校验值：{}", method.getName(), argValue);

        // 如果找不到需要校验的参数直接放行
        if (argValue == null) return jp.proceed();

        // 通过函数式接口传入需要校验的值， 内部会调用工具类的isPhone方法进行校验
        Boolean res = value.function.apply(argValue);

        if (res) {
            return jp.proceed(); // 校验成功则放行
        }else {
            // 校验失败抛出异常（带上错误信息msg）并交给调用方捕获（调用方：使用该注解的项目可以定义全局异常捕获，遇到IllegalArgumentException异常则返回对应报错信息）
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 获取方法信息
     */
    public Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        return jp.getTarget().getClass() // 获取切入点的目标（被修饰方法）的Class对象
                .getMethod(methodSignature.getName(), methodSignature.getParameterTypes()); // 通过方法名和方法参数类型使用反射获取到方法对象
    }

    /**
     * 获取方法的所有参数名字
     */
    private String[] getParamName(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        return methodSignature.getParameterNames();
    }

    /**
     * 获取需要检验的参数值
     * @param target 需要校验的参数名
     * @param args 被修饰的方法中所有的参数
     * @param paramNames 被修饰的方法中所有的参数名
     */
    private Object getArgValue(String target, Object[] args, String[] paramNames){
        // 标记当前遍历的索引（因为args和paramNames是一一对应的）
        int idx = 0;
        // 遍历参数名
        for (String name : paramNames) {
            // 匹配对应的参数名则直接返回对应的参数值
            if (name.equals(target)) {
                return args[idx];
            }
            idx++;
        }
        return null;
    }
}
