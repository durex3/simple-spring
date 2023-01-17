package org.simpleframework.conig;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanFactoryPostProcessor;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 21:08:53
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
        System.out.println("MyBeanFactoryPostProcessor postProcessBeanFactory 执行了, beanDefinitionCount=" + beanDefinitionCount);
    }
}
