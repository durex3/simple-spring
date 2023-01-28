package org.simpleframework.aop;

import java.lang.reflect.Method;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-28 19:31:18
 */
public interface AfterReturningAdvice {

    /**
     * <h2>在目标方法返回前调用</h2>
     *
     * @param returnValue 方法返回值
     * @param method      目标方法
     * @param args        参数
     * @param target      目标对象
     */
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
