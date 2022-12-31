package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <h1>在 BeanFactory 中使用的简单对象实例化策略</h1>
 *
 * @Author: liugelong
 * @createTime: 2022-12-31 22:13:41
 * @version: 1.0
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(RootBeanDefinition rd, String beanName, Constructor<?> ctor, Object... args) throws BeansException {
        Class<?> clazz = rd.getBeanClass();
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
}
