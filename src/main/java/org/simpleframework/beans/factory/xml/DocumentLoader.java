package org.simpleframework.beans.factory.xml;

import org.w3c.dom.Document;

import java.io.InputStream;

/**
 * <h1>文档读取器接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-12 21:38:52
 */
public interface DocumentLoader {

    /**
     * <h2>读取文档</h2>
     *
     * @param inputStream 输入流
     * @return {@link Document}
     */
    Document loadDocument(InputStream inputStream) throws Exception;
}
