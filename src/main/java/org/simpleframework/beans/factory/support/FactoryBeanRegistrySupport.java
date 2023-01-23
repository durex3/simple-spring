package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.BeanCreationException;
import org.simpleframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>加入 factory bean 支持的注册中心</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-23 19:31:17
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);

    protected Object getCachedObjectForFactoryBean(String beanName) {
        return this.factoryBeanObjectCache.get(beanName);
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        if (factory.isSingleton() && containsSingleton(beanName)) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                if (containsSingleton(beanName)) {
                    this.factoryBeanObjectCache.put(beanName, object);
                }
            }
            return object;
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeanCreationException(beanName + " FactoryBean threw exception on object creation", e);
        }
    }
}
