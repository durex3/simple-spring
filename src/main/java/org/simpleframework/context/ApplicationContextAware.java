package org.simpleframework.context;

import org.simpleframework.beans.factory.Aware;

/**
 * <h1>实现此接口可以获取 ApplicationContext 对象</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-20 19:37:46
 */
public interface ApplicationContextAware extends Aware {

    /**
     * <h2>设置 applicationContext</h2>
     *
     * @param applicationContext 应用上下文
     */
    void setApplicationContext(ApplicationContext applicationContext);
}
