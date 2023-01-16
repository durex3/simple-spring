package org.simpleframework.context.support;

import org.simpleframework.beans.BeansException;

/**
 * <h1>从类路径中获取 xml 配置文件的应用上下文</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 12:04:36
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    public ClassPathXmlApplicationContext(String... configLocations) throws BeansException {
        setConfigLocations(configLocations);
        refresh();
    }
}
