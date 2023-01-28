package com.durex.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.simpleframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 21:56:16
 */
@Aspect
@Component
public class AspectConfig {

    @Pointcut("execution(public * com.durex.aop.*.*(..))")
    public void pointcut() {

    }

    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("前置增强: 登录校验, 参数===" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturning(Object result) {
        System.out.println("返回增强: 返回值===" + result);
    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        System.out.println("异常增强: " + e);
    }
}
