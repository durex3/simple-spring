package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.BeansException;

/**
 * <h1>用于 bean 实例化前后的回调</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 19:30:04
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * <h2>bean 实例化前执行，可能直接返回代理对象</h2>
     *
     * @param beanClass 类型
     * @param beanName  bean 名字
     * @return bean
     */
    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * <h2>bean 实例化后执行，但在 属性填充发生之前执行操作</h2>
     *
     * @param bean     bean
     * @param beanName bean 名字
     * @return true 正在填充属性 false 跳过属性填充
     * 返回 false 还将阻止在此 Bean 实例上调用任何后续的 InstantiationAwareBeanPostProcessor 实例。
     */
    default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }
}
