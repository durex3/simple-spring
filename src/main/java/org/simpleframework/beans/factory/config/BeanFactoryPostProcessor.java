package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.BeansException;

/**
 * <h1>BeanFactory 的后置处理器</h1>
 * <p1>BeanFactoryPostProcessor 是针对 BeanFactory 的扩展，主要用在 bean 实例化之前，读取 bean 的定义，并可以修改它</p1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 13:00:21
 */
public interface BeanFactoryPostProcessor {

    /**
     * <h2>未实例化的 bean 可以修改 bean 的定义信息</h2>
     *
     * @param beanFactory 应用程序上下文使用的 Bean 工厂
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
