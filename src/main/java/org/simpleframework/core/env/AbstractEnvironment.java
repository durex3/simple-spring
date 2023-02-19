package org.simpleframework.core.env;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <h1>抽象的环境对象</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 13:36:05
 */
public abstract class AbstractEnvironment implements Environment {

    private final List<PropertySource<?>> propertySources = new CopyOnWriteArrayList<>();
    private final PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(this.propertySources);

    @Override
    public List<PropertySource<?>> getPropertySources() {
        return this.propertySources;
    }

    @Override
    public boolean containsProperty(String key) {
        return propertyResolver.containsProperty(key);
    }

    @Override
    public String getProperty(String key) {
        return propertyResolver.getProperty(key);
    }
}
