package org.simpleframework.context;

import org.simpleframework.beans.factory.Aware;
import org.simpleframework.core.env.Environment;

/**
 * <h1>实现此接口可以获取 EnvironmentAware 对象</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 14:01:21
 */
public interface EnvironmentAware extends Aware {

    void setEnvironment(Environment environment);
}
