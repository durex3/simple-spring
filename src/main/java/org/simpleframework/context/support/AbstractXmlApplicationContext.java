package org.simpleframework.context.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.support.DefaultListableBeanFactory;
import org.simpleframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.simpleframework.core.io.Resource;

import java.io.IOException;

/**
 * <h1>可以从 xml 文件中 读取配置的应用上下文</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 11:48:09
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
        // 创建基于 xml 配置文件 bean 的定义信息读取器
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        // resource loading environment.
        beanDefinitionReader.setResourceLoader(this);
        loadBeanDefinitions(beanDefinitionReader);
    }

    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException {
        Resource[] configResources = getConfigResources();
        if (configResources != null) {
            reader.loadBeanDefinitions(configResources);
        }
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            reader.loadBeanDefinitions(configLocations);
        }
    }

    protected Resource[] getConfigResources() {
        return null;
    }
}
