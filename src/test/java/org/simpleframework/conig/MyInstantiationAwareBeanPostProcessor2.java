package org.simpleframework.conig;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 21:50:41
 */
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
