package org.simpleframework.core.io.support;

import org.simpleframework.core.io.Resource;
import org.simpleframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 19:30:26
 */
public interface ResourcePatternResolver extends ResourceLoader {

    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
    String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /**
     * <h2>将给定的路径模式解析为资源对象</h2>
     *
     * @param locationPattern 要解析的路径模式
     * @return {@link   Resource[]}
     */
    Resource[] getResources(String locationPattern) throws IOException;
}
