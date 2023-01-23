package org.simpleframework.util;

import org.simpleframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.simpleframework.context.annotation.Scope;
import org.simpleframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-21 19:32:23
 */
public final class AnnotationConfigUtils {

    private AnnotationConfigUtils() {
    }

    public static void processCommonDefinitionAnnotations(AnnotatedBeanDefinition abd) {
        AnnotationMetadata metadata = abd.getMetadata();
        Map<Class<? extends Annotation>, Map<String, Object>> attributes = metadata.getAnnotationAttributes();
        Map<String, Object> scopeValue = attributes.get(Scope.class);
        if (Objects.nonNull(scopeValue) && scopeValue.get("value") != null) {
            abd.setScope(scopeValue.get("value").toString());
        }
    }
}
