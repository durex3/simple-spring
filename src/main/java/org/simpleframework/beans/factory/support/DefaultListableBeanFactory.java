package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.BeanDefinitionStoreException;
import org.simpleframework.beans.factory.NoSuchBeanDefinitionException;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>默认实现，基于 bean 定义成熟 bean 工厂</h1>
 * <p>定义 BeanDefinition map</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 12:46:16
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NoSuchBeanDefinitionException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        BeanDefinition existingDefinition = this.beanDefinitionMap.get(beanName);
        if (existingDefinition != null) {
            throw new BeanDefinitionStoreException("Cannot register bean definition [" + beanDefinition + "] for bean '" + beanName + "': There is already [" + existingDefinition + "] bound.");
        }
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        String[] beanNames = getBeanDefinitionNames();
        for (String beanName : beanNames) {
            RootBeanDefinition bd = getMergedBeanDefinition(getBeanDefinition(beanName));
            if (bd.isSingleton() && !bd.isLazyInit()) {
                getBean(beanName);
            }
        }
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> beanMap = new HashMap<>(beanDefinitionMap.size());
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class<?> beanClass;
            try {
                beanClass = Class.forName(beanDefinition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
            if (type.isAssignableFrom(beanClass)) {
                beanMap.put(beanName, (T) getBean(beanName));
            }
        });
        return beanMap;
    }

    @Override
    public void destroySingletons() {
        super.destroySingletons();
    }
}
