package org.simpleframework.aop.framework;

import net.sf.cglib.proxy.Enhancer;

/**
 * <h1>aop 返回代理</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:53:53
 */
public class CglibAopProxy implements AopProxy {

    @Override
    public Object getProxy(AdvisedSupport config) {
        return Enhancer.create(config.getTargetSource().getTargetClass(), new DynamicAdvisedInterceptor(config));
    }
}
