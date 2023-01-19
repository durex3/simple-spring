package com.durex.config;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanFactoryPostProcessor;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 21:18:27
 */
@Component
public class MyBeanFactoryPostProcessor2 implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
        System.out.println("MyBeanFactoryPostProcessor2 postProcessBeanFactory 执行了, beanDefinitionCount=" + beanDefinitionCount);
    }
}
