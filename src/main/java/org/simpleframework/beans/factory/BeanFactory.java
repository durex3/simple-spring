package org.simpleframework.beans.factory;

import org.simpleframework.beans.BeansException;

/**
 * <h1>最基础的 bean 工厂</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-30 22:18:53
 */
public interface BeanFactory {

    String FACTORY_BEAN_PREFIX = "&";

    /**
     * <h2>按名字获取 bean</h2>
     *
     * @param name 名字
     * @return bean
     */
    Object getBean(String name) throws BeansException;

    /**
     * <h2>按名字获取 bean</h2>
     *
     * @param name 名字
     * @param args 参数
     * @return bean
     */
    Object getBean(String name, Object... args) throws BeansException;
}
