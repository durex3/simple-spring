package org.simpleframework.context;

import org.simpleframework.beans.factory.ListableBeanFactory;
import org.simpleframework.core.io.support.ResourcePatternResolver;

/**
 * <h1>应用程序上下文</h1>
 * <p>可以在 Bean 初始化过程中，完成对 Bean 对象的扩展</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-15 19:28:57
 */
public interface ApplicationContext extends ListableBeanFactory, ResourcePatternResolver {

    /**
     * <h2>返回应用程序上下文的唯一 id</h2>
     *
     * @return id
     */
    String getId();

    /**
     * <h2>返回应用上下文的名字</h2>
     *
     * @return 名字 默认为空
     */
    String getApplicationName();
}
