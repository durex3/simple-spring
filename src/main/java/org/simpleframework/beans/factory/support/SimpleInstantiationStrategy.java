package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <h1>在 BeanFactory 中使用的简单对象实例化策略</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 22:13:41
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(RootBeanDefinition bd, String beanName, Constructor<?> ctor, Object... args) throws BeansException {
        Class<?> clazz = bd.getBeanClass();
        try {
            if (null != ctor) {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }

    @Override
    public Object instantiate(RootBeanDefinition bd, String beanName, Object factoryBean, Method factoryMethod, Object... args) throws BeansException {
        Object result;
        Class<?> clazz = bd.getBeanClass();
        try {
            result = factoryMethod.invoke(factoryBean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
        return result;
    }
}
