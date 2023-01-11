package org.simpleframework.core.io;

import java.io.IOException;
import java.net.URL;

/**
 * <h1>文件或类路径资源描述符的接口。</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 19:30:50
 */
public interface Resource extends InputStreamSource {

    /**
     * <h2>返回此资源的 URL </h2>
     *
     * @return {@link  URL}
     */
    URL getURL() throws IOException;

}
