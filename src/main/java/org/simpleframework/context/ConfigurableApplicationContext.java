package org.simpleframework.context;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * <h1></h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-15 19:53:52
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * <h2>设置此应用程序上下文的唯一 id</h2>
     *
     * @param id id
     */
    void setId(String id);

    /**
     * <h2>加载或刷新配置，可能来自基于 Java 的配置、XML 文件、属性文件、注解等</h2>
     */
    void refresh() throws BeansException, IllegalStateException;

    /**
     * <h2>添加后置处理器</h2>
     *
     * @param postProcessor BeanFactory 后置处理器
     */
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
}
