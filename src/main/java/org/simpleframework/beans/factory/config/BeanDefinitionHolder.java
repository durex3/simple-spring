package org.simpleframework.beans.factory.config;

/**
 * <h1>带有名称 BeanDefinition</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-19 23:34:24
 */
public class BeanDefinitionHolder {

    private final BeanDefinition beanDefinition;

    private final String beanName;


    public BeanDefinitionHolder(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    public String getBeanName() {
        return beanName;
    }
}
