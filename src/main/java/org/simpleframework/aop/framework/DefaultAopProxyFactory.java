package org.simpleframework.aop.framework;

import java.lang.reflect.Proxy;

/**
 * <h1>默认的 aop 代理工厂的实现</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:50:47
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(AdvisedSupport config) {
        Class<?> targetClass = config.getTargetSource().getTargetClass();
        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
            return new JdkDynamicAopProxy();
        }
        return new CglibAopProxy();
    }
}
