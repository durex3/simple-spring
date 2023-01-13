package org.simpleframework.beans.factory.xml;

import org.simpleframework.beans.factory.BeanDefinitionStoreException;
import org.simpleframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;
import org.simpleframework.core.io.Resource;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;

/**
 * <h1>xml bean 定义信息读取器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-12 21:17:57
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private final DocumentLoader documentLoader = new DefaultDocumentLoader();

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        if (resource == null) {
            throw new IllegalArgumentException("Resource must not be null");
        }

        try {
            InputStream inputStream = resource.getInputStream();
            return doLoadBeanDefinitions(inputStream);
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource, e);
        }
    }

    public int registerBeanDefinitions(Document doc) {
        BeanDefinitionDocumentReader documentReader = new DefaultBeanDefinitionDocumentReader();
        int countBefore = getRegistry().getBeanDefinitionCount();
        documentReader.registerBeanDefinitions(doc, new XmlReaderContext(this));
        return getRegistry().getBeanDefinitionCount() - countBefore;
    }

    protected int doLoadBeanDefinitions(InputStream inputStream) {
        Document doc;
        try {
            doc = doLoadDocument(inputStream);
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("Unexpected exception parsing XML document from " + inputStream, e);
        }
        return registerBeanDefinitions(doc);
    }

    protected Document doLoadDocument(InputStream inputStream) throws Exception {
        return this.documentLoader.loadDocument(inputStream);
    }
}
