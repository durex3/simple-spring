package org.simpleframework.aop.framework;

/**
 * <h1>代理工厂</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:42:09
 */
public class ProxyFactory extends AdvisedSupport {
    private final AopProxyFactory aopProxyFactory;

    public ProxyFactory() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public Object getProxy() {
        return createAopProxy().getProxy(this);
    }

    protected final synchronized AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(this);
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }
}
