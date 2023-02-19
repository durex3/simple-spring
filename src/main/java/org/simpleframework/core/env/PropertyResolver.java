package org.simpleframework.core.env;

/**
 * <h1>属性映射接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 13:28:53
 */
public interface PropertyResolver {

    boolean containsProperty(String key);

    String getProperty(String key);
}
