package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.BeanFactory;
import org.simpleframework.beans.factory.config.BeanDefinition;

/**
 * <h1>抽象的 bean 工厂</h1>
 * <p>定义 获取 bean 的定义信息</p>
 * <p>定义 创建 bean</p>
 *
 * @Author: liugelong
 * @createTime: 2022-12-31 12:35:34
 * @version: 1.0
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }
}
