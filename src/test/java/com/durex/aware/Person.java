package com.durex.aware;

import org.simpleframework.beans.factory.BeanClassLoaderAware;
import org.simpleframework.beans.factory.BeanFactory;
import org.simpleframework.beans.factory.BeanFactoryAware;
import org.simpleframework.beans.factory.BeanNameAware;
import org.simpleframework.context.ApplicationContext;
import org.simpleframework.context.ApplicationContextAware;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-20 19:47:42
 */
@Component
public class Person implements BeanNameAware, BeanFactoryAware, BeanClassLoaderAware, ApplicationContextAware {

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("获取到 ClassLoader ==== " + classLoader.getClass().getName());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        System.out.println("获取到 BeanFactory ==== " + beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("获取到 BeanName ==== " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        System.out.println("获取到 ApplicationContext ==== " + applicationContext.getId());
    }
}
