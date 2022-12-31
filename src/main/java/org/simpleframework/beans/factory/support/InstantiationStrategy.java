package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;

import java.lang.reflect.Constructor;

/**
 * <h1>bean 实例化策略</h1>
 *
 * @Author: liugelong
 * @createTime: 2022-12-31 21:29:32
 * @version: 1.0
 */
public interface InstantiationStrategy {

    /**
     * <h2>创建实例方法</h2>
     *
     * @param rd       bean 定义信息
     * @param beanName bean 名字
     * @param ctor     构造函数
     * @param args     参数
     * @return 实例
     */
    Object instantiate(RootBeanDefinition rd, String beanName, Constructor<?> ctor, Object... args) throws BeansException;
}
