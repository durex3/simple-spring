package org.simpleframework.core.io.support;

import org.simpleframework.core.env.PropertySource;
import org.simpleframework.core.io.Resource;

import java.io.IOException;

/**
 * <h1>属性资源工厂的默认实现</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 12:47:41
 */
public class DefaultPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, Resource resource) throws IOException {
        return new ResourcePropertySource(name, resource);
    }
}
