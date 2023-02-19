package org.simpleframework.core.io.support;

import org.simpleframework.core.env.PropertySource;
import org.simpleframework.core.io.Resource;

import java.io.IOException;

/**
 * <h1>创建属性资源的工厂</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-18 13:07:40
 */
public interface PropertySourceFactory {

    PropertySource<?> createPropertySource(String name, Resource resource) throws IOException;
}
