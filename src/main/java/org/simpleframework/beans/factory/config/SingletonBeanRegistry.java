package org.simpleframework.beans.factory.config;

/**
 * <h1>单例注册中心</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-30 23:07:25
 */
public interface SingletonBeanRegistry {

    /**
     * <h2>获取单实例 bean</h2>
     *
     * @param beanName bean 名字
     * @return bean
     */
    Object getSingleton(String beanName);
}
