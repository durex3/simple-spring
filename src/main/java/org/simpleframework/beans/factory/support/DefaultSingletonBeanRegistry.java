package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.DisposableBean;
import org.simpleframework.beans.factory.ObjectFactory;
import org.simpleframework.beans.factory.config.SingletonBeanRegistry;

import java.util.LinkedHashMap;
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
    private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
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

    public void destroySingletons() {
        String[] disposableBeanNames;
        synchronized (this.disposableBeans) {
            disposableBeanNames = this.disposableBeans.keySet().toArray(new String[0]);
        }
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            destroySingleton(disposableBeanNames[i]);
        }
        synchronized (this.singletonObjects) {
            this.singletonObjects.clear();
        }
    }

    public void destroySingleton(String beanName) {
        removeSingleton(beanName);

        DisposableBean disposableBean;
        synchronized (this.disposableBeans) {
            disposableBean = (DisposableBean) this.disposableBeans.remove(beanName);
        }
        if (disposableBean != null) {
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException(beanName + " destroy threw an exception" + e);
            }
        }
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        synchronized (this.disposableBeans) {
            this.disposableBeans.put(beanName, bean);
        }
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
