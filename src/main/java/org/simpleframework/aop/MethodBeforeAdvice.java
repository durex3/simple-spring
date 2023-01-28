package org.simpleframework.aop;

import java.lang.reflect.Method;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 13:53:31
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * <h2>在目标方法之前调用</h2>
     *
     * @param method 目标方法
     * @param args   参数
     * @param target 目标对象
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
