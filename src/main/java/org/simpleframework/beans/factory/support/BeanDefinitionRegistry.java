package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.config.BeanDefinition;

/**
 * <h1>用于保存 bean 定义的注册表的接口</h1>
 *
 * @Author: liugelong
 * @createTime: 2022-12-31 12:47:41
 * @version: 1.0
 */
public interface BeanDefinitionRegistry {

    /**
     * <h2>注册 bean 的定义信息</h2>
     *
     * @param beanName       bean 名字
     * @param beanDefinition 定义信息
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}