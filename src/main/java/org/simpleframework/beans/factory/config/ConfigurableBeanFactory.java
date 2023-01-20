package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.factory.BeanFactory;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 13:30:09
 */
public interface ConfigurableBeanFactory extends SingletonBeanRegistry, BeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * <h2>销毁 bean 实例</h2>
     */
    void destroySingletons();

    ClassLoader getBeanClassLoader();
}
