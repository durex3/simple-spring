package org.simpleframework.beans.factory;

import org.simpleframework.beans.BeansException;

/**
 * <h1>最基础的 bean 工厂</h1>
 *
 * @Author: liugelong
 * @createTime: 2022-12-30 22:18:53
 * @version: 1.0
 */
public interface BeanFactory {

    /**
     * <h1>按名字获取 bean</h1>
     *
     * @param name 名字
     * @return bean
     */
    Object getBean(String name) throws BeansException;
}
