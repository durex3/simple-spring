package org.simpleframework.core.type.classreading;

import org.simpleframework.core.type.AnnotationMetadata;
import org.simpleframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-11 13:27:07
 */
public class SimpleAnnotationMetadata implements AnnotationMetadata {

    private Set<String> annotationTypes;
    private Set<Annotation> annotations;

    public SimpleAnnotationMetadata(Set<Annotation> annotations) {
        this.annotations = annotations;
        this.annotationTypes = annotations.stream().map(a -> a.annotationType().getName())
                .collect(Collectors.toSet());
    }

    public SimpleAnnotationMetadata() {

    }

    @Override
    public Set<String> getAnnotationTypes() {
        return this.annotationTypes;
    }

    @Override
    public boolean hasAnnotation(String annotationName) {
        return annotationTypes.contains(annotationName);
    }

    @Override
    public Map<String, Object> getAnnotationAttributes(String annotationName) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals(annotationName)) {
                InvocationHandler handler = Proxy.getInvocationHandler(annotation);
                return (Map<String, Object>) ReflectionUtils.getFieldValue(handler, "memberValues");
            }
        }
        return new HashMap<>();
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }
}
