package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>默认单例注册中心实现</h1>
 * <p>定义单例的 bean map</p>
 *
 * @Author: liugelong
 * @createTime: 2022-12-30 23:09:03
 * @version: 1.0
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
