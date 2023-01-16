package org.simpleframework.conig;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanPostProcessor;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 21:31:56
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor postProcessBeforeInitialization 执行了");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor postProcessAfterInitialization 执行了");
        return bean;
    }
}
