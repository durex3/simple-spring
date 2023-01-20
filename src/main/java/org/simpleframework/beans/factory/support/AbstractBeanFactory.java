package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.BeanPostProcessor;
import org.simpleframework.beans.factory.config.ConfigurableBeanFactory;
import org.simpleframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.simpleframework.util.ClassUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <h1>抽象的 bean 工厂</h1>
 * <p>定义 获取 bean 的定义信息</p>
 * <p>定义 创建 bean</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 12:35:34
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();
    private volatile boolean hasInstantiationAwareBeanPostProcessors;
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, RootBeanDefinition mbd, Object... args) throws BeansException;

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        if (beanPostProcessor == null) {
            throw new IllegalArgumentException("BeanPostProcessor must not be null");
        }
        this.beanPostProcessors.remove(beanPostProcessor);
        if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
            this.hasInstantiationAwareBeanPostProcessors = true;
        }
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    protected Object doGetBean(String name, Object[] args) {
        BeanDefinition beanDefinition = getBeanDefinition(name);
        RootBeanDefinition rootBeanDefinition = getMergedBeanDefinition(beanDefinition);
        return getSingleton(name, () -> createBean(name, rootBeanDefinition, args));
    }

    protected RootBeanDefinition getMergedBeanDefinition(BeanDefinition bd) {
        if (bd instanceof RootBeanDefinition) {
            return ((RootBeanDefinition) bd).cloneBeanDefinition();
        }
        return new RootBeanDefinition(bd);
    }

    protected boolean hasInstantiationAwareBeanPostProcessors() {
        return this.hasInstantiationAwareBeanPostProcessors;
    }
}
