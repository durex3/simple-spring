package org.simpleframework.context.support;

import org.apache.commons.lang3.ObjectUtils;
import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanDefinitionRegistryPostProcessor;
import org.simpleframework.beans.factory.config.BeanFactoryPostProcessor;
import org.simpleframework.beans.factory.config.BeanPostProcessor;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;
import org.simpleframework.context.ConfigurableApplicationContext;
import org.simpleframework.core.io.DefaultResourceLoader;
import org.simpleframework.core.io.Resource;
import org.simpleframework.core.io.support.PathMatchingResourcePatternResolver;
import org.simpleframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <h1>{@link org.simpleframework.context.ApplicationContext} 接口的抽象实现。</h1>
 * <p>实现通用上下文功能。使用模板方法设计模式，需要具体的子类来实现抽象方法。</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-15 19:48:32
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    private String id = ObjectUtils.identityToString(this);
    private final ResourcePatternResolver resourcePatternResolver;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    protected AbstractApplicationContext() {
        this.resourcePatternResolver = getResourcePatternResolver();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        // 创建 BeanFactory
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        // 执行 BeanFactory 的后置处理器
        invokeBeanFactoryPostProcessors(beanFactory);
        // 注册 Bean 的后置处理器
        registerBeanPostProcessors(beanFactory);
        // 实例化所有剩余（非惰性初始化）单例
        finishBeanFactoryInitialization(beanFactory);
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return this.resourcePatternResolver.getResources(locationPattern);
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    protected ResourcePatternResolver getResourcePatternResolver() {
        return new PathMatchingResourcePatternResolver(this);
    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        List<BeanFactoryPostProcessor> postProcessors = getBeanFactoryPostProcessors();
        Map<String, BeanFactoryPostProcessor> postProcessorMap = getBeansOfType(BeanFactoryPostProcessor.class);
        postProcessors.addAll(postProcessorMap.values());

        if (beanFactory instanceof BeanDefinitionRegistry) {
            List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
                if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
                    BeanDefinitionRegistryPostProcessor registryProcessor = (BeanDefinitionRegistryPostProcessor) postProcessor;
                    registryProcessor.postProcessBeanDefinitionRegistry(registry);
                } else {
                    regularPostProcessors.add(postProcessor);
                }
            }
            invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
        } else {
            invokeBeanFactoryPostProcessors(postProcessors, beanFactory);
        }
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> postProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        registerBeanPostProcessors(beanFactory, postProcessorMap.values());
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

    private static void invokeBeanFactoryPostProcessors(Collection<? extends BeanFactoryPostProcessor> postProcessors, ConfigurableListableBeanFactory beanFactory) {
        postProcessors.forEach(postProcessor -> postProcessor.postProcessBeanFactory(beanFactory));
    }

    private static void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory, Collection<BeanPostProcessor> postProcessors) {
        postProcessors.forEach(beanFactory::addBeanPostProcessor);
    }
}
