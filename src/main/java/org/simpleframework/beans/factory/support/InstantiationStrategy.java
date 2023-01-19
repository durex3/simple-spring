package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * <h1>bean 实例化策略</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 21:29:32
 */
public interface InstantiationStrategy {

    /**
     * <h2>创建实例方法</h2>
     *
     * @param bd       bean 定义信息
     * @param beanName bean 名字
     * @param ctor     构造函数
     * @param args     参数
     * @return 实例
     */
    Object instantiate(RootBeanDefinition bd, String beanName, Constructor<?> ctor, Object... args) throws BeansException;

    /**
     * <h2>调用方法返回实例</h2>
     *
     * @param bd            bean 定义信息
     * @param beanName      bean 名字
     * @param factoryBean   工厂对象
     * @param factoryMethod 工厂方法
     * @param args          参数
     * @return 实例
     */
    Object instantiate(RootBeanDefinition bd, String beanName, Object factoryBean, Method factoryMethod, Object... args) throws BeansException;
}
