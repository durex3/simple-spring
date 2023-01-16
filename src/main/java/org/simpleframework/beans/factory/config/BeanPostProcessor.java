package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.BeansException;

/**
 * <h1>bean 的后置处理器 </h1>
 * <p>BeanPostProcessor 是针对 bean 的扩展，主要用在 bean 实例化之后，执行初始化方法前后，允许开发者对 bean 实例进行修改</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 13:41:31
 */
public interface BeanPostProcessor {

    /**
     * <h2>bean 初始化前执行/h2>
     *
     * @param bean     bean
     * @param beanName bean 名字
     * @return bean
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * <h2>bean 初始化后执行</h2>
     *
     * @param bean     bean
     * @param beanName bean 名字
     * @return bean
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
