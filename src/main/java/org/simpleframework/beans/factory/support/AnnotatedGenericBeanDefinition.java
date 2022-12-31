package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.config.BeanDefinition;

/**
 * @Author: liugelong
 * @createTime: 2022-12-31 13:22:03
 * @version: 1.0
 */
public class AnnotatedGenericBeanDefinition extends AbstractBeanDefinition {

    public AnnotatedGenericBeanDefinition(Class<?> beanClass) {
        super();
        setBeanClass(beanClass);
    }
}
