package org.simpleframework.aop;

import java.lang.reflect.Method;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-28 19:31:18
 */
public interface AfterThrowingAdvice {

    /**
     * <h2>在目标方法异常时调用</h2>
     *
     * @param e      目标方法的异常
     * @param method 目标方法
     * @param args   参数
     * @param target 目标对象
     */
    void afterThrowing(Throwable e, Method method, Object[] args, Object target) throws Throwable;
}
