package org.simpleframework.beans.factory.xml;

import org.simpleframework.beans.factory.BeanDefinitionStoreException;
import org.w3c.dom.Document;

/**
 * <h1>bean 定义信息文档读取器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-12 21:31:44
 */
public interface BeanDefinitionDocumentReader {

    /**
     * <h2>从文档中注册 bean 的定义信息</h2>
     *
     * @param doc     文档对象
     * @param context xml 读取器上下文
     */
    void registerBeanDefinitions(Document doc, XmlReaderContext context) throws BeanDefinitionStoreException;
}
