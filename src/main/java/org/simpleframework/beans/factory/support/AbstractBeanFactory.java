package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.BeanFactory;
import org.simpleframework.beans.factory.FactoryBean;
import org.simpleframework.beans.factory.NoSuchBeanDefinitionException;
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
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();
    private volatile boolean hasInstantiationAwareBeanPostProcessors;
    private final ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

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

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        String beanName = transformedBeanName(name);
        Object beanInstance = getSingleton(beanName);
        if (beanInstance != null && beanInstance.getClass() != null) {
            if (beanInstance instanceof FactoryBean && !isFactoryDereference(name)) {
                return ((FactoryBean<?>) beanInstance).getObjectType();
            } else {
                return beanInstance.getClass();
            }
        }
        RootBeanDefinition rd = getMergedBeanDefinition(getBeanDefinition(beanName));
        if (rd.getFactoryMethodName() != null) {
            return rd.getResolvedFactoryMethod().getReturnType();
        }
        Class<?> beanClass = rd.getBeanClass();
        return !isFactoryDereference(name) ? beanClass : null;
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        String beanName = transformedBeanName(name);

        Object beanInstance = getSingleton(beanName);
        if (beanInstance != null) {
            if (beanInstance instanceof FactoryBean) {
                return (isFactoryDereference(name) || ((FactoryBean<?>) beanInstance).isSingleton());
            } else {
                return !isFactoryDereference(name);
            }
        }

        RootBeanDefinition rd = getMergedBeanDefinition(getBeanDefinition(beanName));

        if (rd.isSingleton()) {
            Class<?> beanType = rd.getBeanClass();
            if (beanType != null && FactoryBean.class.isAssignableFrom(beanType)) {
                if (isFactoryDereference(name)) {
                    return true;
                }
                FactoryBean<?> factoryBean = (FactoryBean<?>) getBean(FACTORY_BEAN_PREFIX + beanName);
                return factoryBean.isSingleton();
            } else {
                return !isFactoryDereference(name);
            }
        }

        return false;
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public boolean isFactoryDereference(String name) {
        return (name != null && name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX));
    }

    protected <T> T doGetBean(String name, Object[] args) {
        String beanName = transformedBeanName(name);
        Object sharedInstance = getSingleton(beanName);
        Object bean;
        if (sharedInstance != null) {
            bean = getObjectForBeanInstance(sharedInstance, name, beanName);
        } else {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            RootBeanDefinition rootBeanDefinition = getMergedBeanDefinition(beanDefinition);
            if (beanDefinition.isSingleton()) {
                sharedInstance = getSingleton(beanName, () -> createBean(beanName, rootBeanDefinition, args));
                bean = getObjectForBeanInstance(sharedInstance, name, beanName);
            } else if (beanDefinition.isPrototype()) {
                sharedInstance = createBean(beanName, rootBeanDefinition, args);
                bean = getObjectForBeanInstance(sharedInstance, name, beanName);
            } else {
                throw new IllegalStateException("No Scope registered for scope name '" + beanDefinition.getScope() + "'");
            }
        }
        return (T) bean;
    }

    protected String transformedBeanName(String name) {
        if (!name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX)) {
            return name;
        }
        return name.substring(BeanFactory.FACTORY_BEAN_PREFIX.length());
    }

    protected RootBeanDefinition getMergedBeanDefinition(BeanDefinition bd) {
        if (bd instanceof RootBeanDefinition) {
            return ((RootBeanDefinition) bd).cloneBeanDefinition();
        }
        return new RootBeanDefinition(bd);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String name, String beanName) {
        if (name != null && name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX)) {
            if (!(beanInstance instanceof FactoryBean)) {
                throw new BeansException("Bean named" + name + " is not factory bean");
            }
            return beanInstance;
        }

        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factory = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factory, beanName);
        }

        return object;
    }

    protected boolean hasInstantiationAwareBeanPostProcessors() {
        return this.hasInstantiationAwareBeanPostProcessors;
    }
}
