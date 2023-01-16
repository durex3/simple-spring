package org.simpleframework.beans.factory;

import org.simpleframework.beans.BeansException;

import java.util.Map;

/**
 * <h1>拓展了 BeanFactory </h1>
 * <p>提供查找 Bean 以及 Bean 的定义信息</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-15 19:31:03
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * <h2>是否存在某个 bean 的定义信息</h2>
     *
     * @param beanName bean 名字
     * @return true false
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * <h2>返回 bean 定义信息的数量</h2>
     *
     * @return 数量
     */
    int getBeanDefinitionCount();

    /**
     * <h2>返回所有的 bean 名字</h2>
     *
     * @return {@link String[]}
     */
    String[] getBeanDefinitionNames();

    /**
     * <h2>根据类型返回 bean</h2>
     *
     * @param type 类型
     * @return bean bean map
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
