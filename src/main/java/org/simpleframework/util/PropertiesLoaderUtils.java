package org.simpleframework.util;

import org.simpleframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * <h1>Properties 属性加载工具类</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 14:04:03
 */
public final class PropertiesLoaderUtils {

    private PropertiesLoaderUtils() {
    }


    public static Properties loadProperties(Resource resource) throws IOException {
        Properties props = new Properties();
        props.load(resource.getInputStream());
        return props;
    }
}
