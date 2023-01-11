package org.simpleframework.core.io;

import org.simpleframework.util.ResourceUtils;

/**
 * <h1>资源加载器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 19:27:24
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;

    /**
     * <h2>返回指定资源位置的资源</h2>
     *
     * @param location 资源位置
     * @return {@link Resource}
     */
    Resource getResource(String location);
}
