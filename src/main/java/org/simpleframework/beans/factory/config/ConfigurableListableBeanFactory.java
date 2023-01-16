package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.ListableBeanFactory;

/**
 * <h1>Bean 工厂要实现的配置接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-15 20:13:40
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * <h2>实例化所有非懒加载单例</h2>
     **/
    void preInstantiateSingletons() throws BeansException;

    BeanDefinition getBeanDefinition(String beanName);
}
