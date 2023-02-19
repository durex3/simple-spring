package org.simpleframework.core.env;

/**
 * <h1>抽象的资源映射器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 13:37:38
 */
public abstract class AbstractPropertyResolver implements PropertyResolver {

    @Override
    public boolean containsProperty(String key) {
        return (getProperty(key) != null);
    }

    @Override
    public String getProperty(String key) {
        return null;
    }
}
