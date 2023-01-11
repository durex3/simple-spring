package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.ObjectFactory;
import org.simpleframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>默认单例注册中心实现</h1>
 * <p>定义单例的 bean map</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-30 23:09:03
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        if (beanName == null || beanName.isEmpty()) {
            throw new BeansException("Bean name must not be null");
        }
        synchronized (this.singletonObjects) {
            Object singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {
                singletonObject = singletonFactory.getObject();
                addSingleton(beanName, singletonObject);
            }
            return singletonObject;
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
