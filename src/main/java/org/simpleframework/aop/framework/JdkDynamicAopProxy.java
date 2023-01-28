package org.simpleframework.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <h1>jdk 动态代理</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:56:59
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    @Override
    public Object getProxy(AdvisedSupport config) {
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }
}
