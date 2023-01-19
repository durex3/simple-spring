package com.durex.config;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 21:50:41
 */
@Component
public class MyInstantiationAwareBeanPostProcessor2 implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor2 postProcessBeforeInstantiation 执行了, beanName=" + beanName);
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("MyInstantiationAwareBeanPostProcessor2 postProcessAfterInstantiation 执行了, beanName=" + beanName);
        return true;
    }
}
