package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.NoSuchBeanDefinitionException;
import org.simpleframework.beans.factory.config.BeanDefinition;

/**
 * <h1>用于保存 bean 定义的注册中心的接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 12:47:41
 */
public interface BeanDefinitionRegistry {

    /**
     * <h2>注册 bean 的定义信息</h2>
     *
     * @param beanName       bean 名字
     * @param beanDefinition 定义信息
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);


    /**
     * <h2>返回注册中心定义的 bean 数量</h2>
     *
     * @return 数量
     */
    int getBeanDefinitionCount();

    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    String[] getBeanDefinitionNames();

    boolean containsBeanDefinition(String beanName);
}
