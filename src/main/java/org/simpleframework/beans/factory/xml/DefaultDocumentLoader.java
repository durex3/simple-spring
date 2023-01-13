package org.simpleframework.beans.factory.xml;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * <h1>默认文档读取器实现</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-12 21:40:55
 */
public class DefaultDocumentLoader implements DocumentLoader {

    @Override
    public Document loadDocument(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(inputStream);
    }
}
