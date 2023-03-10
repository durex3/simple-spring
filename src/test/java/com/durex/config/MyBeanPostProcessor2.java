package com.durex.config;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanPostProcessor;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 21:31:56
 */
@Component
public class MyBeanPostProcessor2 implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor2 postProcessBeforeInitialization 执行了, beanName=" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor2 postProcessAfterInitialization 执行了, beanName=" + beanName);
        return bean;
    }
}
