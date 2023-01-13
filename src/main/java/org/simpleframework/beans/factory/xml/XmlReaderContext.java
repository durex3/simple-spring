package org.simpleframework.beans.factory.xml;

import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * <h1>xml 读取器上下文</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-12 22:12:05
 */
public class XmlReaderContext {

    private final XmlBeanDefinitionReader reader;

    public XmlReaderContext(XmlBeanDefinitionReader reader) {
        this.reader = reader;
    }

    public final BeanDefinitionRegistry getRegistry() {
        return this.reader.getRegistry();
    }

    public final XmlBeanDefinitionReader getReader() {
        return this.reader;
    }
}
