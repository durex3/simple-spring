package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 13:22:03
 */
public class RootBeanDefinition extends AbstractBeanDefinition {

    private Method factoryMethodToIntrospect;

    public RootBeanDefinition() {
        super();
    }

    public RootBeanDefinition(Class<?> beanClass) {
        super();
        setBeanClass(beanClass);
    }

    public RootBeanDefinition(BeanDefinition original) {
        super(original);
    }

    @Override
    public RootBeanDefinition cloneBeanDefinition() {
        return new RootBeanDefinition(this);
    }


    public void setResolvedFactoryMethod(Method method) {
        this.factoryMethodToIntrospect = method;
    }

    public Method getResolvedFactoryMethod() {
        return this.factoryMethodToIntrospect;
    }
}
