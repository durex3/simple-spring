package org.simpleframework.beans.factory;

import org.simpleframework.beans.BeansException;

/**
 * <h1>定义一个工厂，该工厂在调用时可以返回Object实例。</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 12:46:57
 */
public interface ObjectFactory<T> {

    /**
     * <h2>返回这个工厂管理的对象的实例</h2>
     *
     * @return 返回一个实例
     * @throws BeansException 异常
     */
    T getObject() throws BeansException;
}
