package org.simpleframework.context;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanFactoryPostProcessor;
import org.simpleframework.core.env.Environment;

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
     * <h2>关闭容器</h2>
     */
    void close();

    /**
     * <h2>向 JVM 运行时注册一个关闭钩子，在 JVM 关闭时关闭此上下文，除非当时它已经关闭</h2>
     */
    void registerShutdownHook();

    /**
     * <h2>添加后置处理器</h2>
     *
     * @param postProcessor BeanFactory 后置处理器
     */
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    /**
     * <h1>设置环境对象</h1>
     *
     * @param environment 环境
     */
    void setEnvironment(Environment environment);

    /**
     * <h1>返回环境对象</h1>
     *
     * @return {@link Environment}
     */
    Environment getEnvironment();
}
