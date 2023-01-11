package org.simpleframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * <h1>读取资源对象的简单接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 19:31:13
 */
public interface InputStreamSource {

    /**
     * <h2>返回输入流</h2>
     *
     * @return {@link InputStream}
     */
    InputStream getInputStream() throws IOException;
}
