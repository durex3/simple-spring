package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * <h1>bean 的注册中心的后置处理器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 13:10:47
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;
}
