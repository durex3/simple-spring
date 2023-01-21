package org.simpleframework.beans.factory.xml;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.beans.factory.BeanDefinitionStoreException;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.RuntimeBeanReference;
import org.simpleframework.beans.factory.support.RootBeanDefinition;
import org.simpleframework.util.ClassUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.beans.Introspector;

/**
 * <h1>默认 bean 定义信息文档读取器实现</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-12 22:00:31
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String SCOPE_ATTRIBUTE = "scope";

    @Override
    public void registerBeanDefinitions(Document doc, XmlReaderContext context) throws BeanDefinitionStoreException {
        Element root = doc.getDocumentElement();
        NodeList beanList = root.getElementsByTagName(BEAN_ELEMENT);
        for (int i = 0; i < beanList.getLength(); i++) {
            Element bean = (Element) beanList.item(i);

            // 解析 bean 标签
            String id = bean.getAttribute(ID_ATTRIBUTE);
            String name = bean.getAttribute(NAME_ATTRIBUTE);
            String className = bean.getAttribute(CLASS_ATTRIBUTE);
            try {
                Class<?> clazz = Class.forName(className);
                String beanName = StringUtils.isNotBlank(id) ? id : name;
                if (StringUtils.isBlank(beanName)) {
                    String shortClassName = ClassUtils.getShortName(clazz.getName());
                    beanName = Introspector.decapitalize(shortClassName);
                }
                BeanDefinition definition = new RootBeanDefinition(clazz);
                parseProperty(bean, definition);
                parseMethod(bean, definition);
                if (bean.hasAttribute(SCOPE_ATTRIBUTE)) {
                    definition.setScope(bean.getAttribute(SCOPE_ATTRIBUTE));
                }
                context.getRegistry().registerBeanDefinition(beanName, definition);
            } catch (ClassNotFoundException e) {
                throw new BeanDefinitionStoreException("Failed to load class with " + className);
            }
        }
    }

    private static void parseMethod(Element bean, BeanDefinition definition) {
        if (bean.hasAttribute(INIT_METHOD_ATTRIBUTE)) {
            String initMethodName = bean.getAttribute(INIT_METHOD_ATTRIBUTE);
            definition.setInitMethodName(initMethodName);
        }
        if (bean.hasAttribute(DESTROY_METHOD_ATTRIBUTE)) {
            String destroyMethodName = bean.getAttribute(DESTROY_METHOD_ATTRIBUTE);
            definition.setDestroyMethodName(destroyMethodName);
        }
    }

    private static void parseProperty(Element element, BeanDefinition definition) {
        // 解析 property 属性
        NodeList propertyList = element.getElementsByTagName(PROPERTY_ELEMENT);
        for (int j = 0; j < propertyList.getLength(); j++) {
            Element property = (Element) propertyList.item(j);
            String propertyName = property.getAttribute(NAME_ATTRIBUTE);
            String propertyValue = property.getAttribute(VALUE_ATTRIBUTE);
            String propertyRef = property.getAttribute(REF_ATTRIBUTE);
            propertyValue = StringUtils.isNotBlank(propertyValue) ? propertyValue : propertyRef;
            if (StringUtils.isNotBlank(propertyName) || StringUtils.isNotBlank(propertyValue)) {
                if (StringUtils.isNotBlank(propertyRef)) {
                    definition.getPropertyValues().addPropertyValue(propertyName, new RuntimeBeanReference(propertyValue));
                } else {
                    definition.getPropertyValues().addPropertyValue(propertyName, propertyValue);
                }
            }
        }
    }
}
