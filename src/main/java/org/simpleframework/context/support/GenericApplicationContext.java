package org.simpleframework.context.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.NoSuchBeanDefinitionException;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;
import org.simpleframework.beans.factory.support.DefaultListableBeanFactory;
import org.simpleframework.core.io.Resource;
import org.simpleframework.core.io.ResourceLoader;

/**
 * <h1>通用的应用上下文</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-17 19:07:29
 */
public abstract class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {


    private final DefaultListableBeanFactory beanFactory;

    private ResourceLoader resourceLoader;

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    public GenericApplicationContext(DefaultListableBeanFactory beanFactory) {
        if (beanFactory == null) {
            throw new IllegalArgumentException("BeanFactory must not be null");
        }
        this.beanFactory = beanFactory;
    }

    @Override
    public Resource getResource(String location) {
        if (this.resourceLoader != null) {
            return this.resourceLoader.getResource(location);
        }
        return super.getResource(location);
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    protected void refreshBeanFactory() throws BeansException, IllegalStateException {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        return this.beanFactory.getBeanDefinition(beanName);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public final DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return this.beanFactory;
    }
}
