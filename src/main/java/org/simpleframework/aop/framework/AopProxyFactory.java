package org.simpleframework.aop.framework;

/**
 * <h1>aop 代理工厂</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:39:58
 */
public interface AopProxyFactory {

    AopProxy createAopProxy(AdvisedSupport config);
}
