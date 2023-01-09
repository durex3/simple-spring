package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * <h1>抽象的 bean 工厂，能够实现自动装配</h1>
 *
 * @Author: liugelong
 * @createTime: 2022-12-31 12:36:43
 * @version: 1.0
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, RootBeanDefinition mbd, Object... args) {
        Object bean;
        try {
            bean = createBeanInstance(beanName, mbd, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    protected Object createBeanInstance(String beanName, RootBeanDefinition mbd, Object... args) {
        Class<?> beanClass = mbd.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        if (declaredConstructors.length == 0) {
            throw new BeansException(beanName + " No default constructor found");
        }
        Constructor<?> constructorToUse = Arrays.stream(declaredConstructors)
                .filter(ctor -> null != args && ctor.getParameterTypes().length == args.length)
                .findFirst().orElseThrow(() -> new BeansException(beanName + " Illegal arguments for constructor"));

        return instantiationStrategy.instantiate(mbd, beanName, constructorToUse, args);
    }
}
