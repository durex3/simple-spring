package org.simpleframework.beans.factory;

/**
 * <h1>如果 Bean 实现了此接口，则将是 bean 的工厂</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-23 18:33:22
 */
public interface FactoryBean<T> {

    /**
     * <h2>返回此工厂管理的对象的实例, 可以是单例或者多例</h2>
     *
     * @return bean 对象
     */
    T getObject() throws Exception;

    /**
     * <h2>返回 bean 对象的类型</h2>
     *
     * @return Class
     */
    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }
}
