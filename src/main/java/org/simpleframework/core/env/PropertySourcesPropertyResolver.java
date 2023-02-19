package org.simpleframework.core.env;

import java.util.List;

/**
 * <h1>属性资源映射器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 13:39:15
 */
public class PropertySourcesPropertyResolver extends AbstractPropertyResolver {

    private final List<PropertySource<?>> propertySources;

    public PropertySourcesPropertyResolver(List<PropertySource<?>> propertySources) {
        this.propertySources = propertySources;
    }

    @Override
    public boolean containsProperty(String key) {
        if (this.propertySources != null) {
            for (PropertySource<?> propertySource : this.propertySources) {
                if (propertySource.containsProperty(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getProperty(String key) {
        for (PropertySource<?> propertySource : propertySources) {
            Object value = propertySource.getProperty(key);
            if (value != null) {
                return value.toString();
            }
        }
        return null;
    }
}
