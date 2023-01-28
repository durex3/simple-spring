package org.simpleframework.aop.framework;

/**
 * <h1>aop 代理接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:41:09
 */
public interface AopProxy {

    /**
     * <h2>获取代理对象</h2>
     *
     * @param config 切面配置
     * @return 代理对象
     */
    Object getProxy(AdvisedSupport config);
}
