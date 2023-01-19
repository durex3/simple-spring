package org.simpleframework.context.annotation;

import org.simpleframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.simpleframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-18 21:43:18
 */
public class ConfigurationClassParser {

    public Set<ConfigurationClass> parse(Set<AnnotatedGenericBeanDefinition> beanDefinitions) {
        Set<ConfigurationClass> result = new LinkedHashSet<>();
        for (AnnotatedGenericBeanDefinition beanDefinition : beanDefinitions) {
            AnnotationMetadata metadata = beanDefinition.getMetadata();
            Class<?> beanClass = beanDefinition.getBeanClass();
            Set<String> types = metadata.getAnnotationTypes();
            if (types.contains("org.simpleframework.context.annotation.Configuration")) {
                ConfigurationClass configurationClass = new ConfigurationClass(metadata);
                // 获取 @Bean methods
                Method[] methods = beanClass.getMethods();
                getBeanMethod(methods).forEach(configurationClass::addBeanMethod);
                result.add(configurationClass);
            }
        }
        return result;
    }

    private Set<Method> getBeanMethod(Method[] methods) {
        Set<Method> result = new LinkedHashSet<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Bean.class)) {
                result.add(method);
            }
        }
        return result;
    }
}
