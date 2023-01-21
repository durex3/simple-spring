package org.simpleframework.core.type;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-11 13:24:13
 */
public interface AnnotationMetadata {

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationName);

    Map<String, Object> getAnnotationAttributes(String annotationName);

    Map<Class<? extends Annotation>, Map<String, Object>> getAnnotationAttributes();
}
