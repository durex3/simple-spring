package org.simpleframework.context.annotation;

import org.simpleframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <h1>表示用户定义的 {@link @Configuration} 类, 包括一组 {@link @Bean} 方法</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-19 12:48:20
 */
public class ConfigurationClass {

    private final AnnotationMetadata metadata;

    private final Set<Method> beanMethods = new LinkedHashSet<>();

    public ConfigurationClass(AnnotationMetadata metadata) {
        this.metadata = metadata;
    }

    public Set<Method> getBeanMethods() {
        return this.beanMethods;
    }

    public AnnotationMetadata getMetadata() {
        return this.metadata;
    }

    public void addBeanMethod(Method method) {
        this.beanMethods.add(method);
    }
}
