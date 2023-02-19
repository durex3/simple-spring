package org.simpleframework.core.io.support;

import org.simpleframework.core.env.PropertiesPropertySource;
import org.simpleframework.core.io.Resource;
import org.simpleframework.util.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 12:52:49
 */
public class ResourcePropertySource extends PropertiesPropertySource {

    public ResourcePropertySource(String name, Properties source) {
        super(name, source);
    }

    public ResourcePropertySource(String name, Resource resource) throws IOException {
        super(name, PropertiesLoaderUtils.loadProperties(resource));
    }
}
