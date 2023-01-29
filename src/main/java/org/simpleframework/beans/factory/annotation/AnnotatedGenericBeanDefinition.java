package org.simpleframework.beans.factory.annotation;

import org.simpleframework.beans.factory.support.RootBeanDefinition;
import org.simpleframework.core.type.AnnotationMetadata;
import org.simpleframework.core.type.classreading.SimpleAnnotationMetadata;
import org.simpleframework.util.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>注解的 bean 定义信息实现</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 20:53:20
 */
public class AnnotatedGenericBeanDefinition extends RootBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;

    public AnnotatedGenericBeanDefinition(Class<?> beanClass) {
        setBeanClass(beanClass);
        Set<Annotation> annotations = AnnotationUtils.getAnnotations(beanClass);
        this.metadata = new SimpleAnnotationMetadata(annotations);
    }

    @Override
    public AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
