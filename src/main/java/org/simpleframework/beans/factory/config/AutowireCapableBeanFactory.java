package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.BeanFactory;

/**
 * <h1>能自动装配的 BeanFactory</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 13:17:12
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * <h2>初始化 bean</h2>
     *
     * @param existingBean bean
     * @param beanName     bean 名字
     * @return bean
     */
    Object initializeBean(Object existingBean, String beanName) throws BeansException;

    /**
     * <h2>bean 初始化前执行后置处理器</h2>
     *
     * @param existingBean bean
     * @param beanName     bean 名字
     * @return bean
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * <h2>bean 初始化后执行后置处理器</h2>
     *
     * @param existingBean bean
     * @param beanName     bean 名字
     * @return bean
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
