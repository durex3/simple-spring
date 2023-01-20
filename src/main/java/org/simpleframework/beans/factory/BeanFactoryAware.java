package org.simpleframework.beans.factory;

/**
 * <h1>实现此接口可以获取 BeanFactory 对象</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-20 19:24:01
 */
public interface BeanFactoryAware extends Aware {

    /**
     * <h2>设置 beanFactory</h2>
     *
     * @param beanFactory bean 工厂
     */
    void setBeanFactory(BeanFactory beanFactory);
}
