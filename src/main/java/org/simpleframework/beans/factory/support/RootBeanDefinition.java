package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.config.BeanDefinition;

/**
 * @Author: liugelong
 * @createTime: 2022-12-31 13:22:03
 * @version: 1.0
 */
public class RootBeanDefinition extends AbstractBeanDefinition {

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
}
