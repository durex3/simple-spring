package org.simpleframework.util;

import java.lang.annotation.Annotation;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-19 13:41:13
 */
public class AnnotationUtils {

    private AnnotationUtils() {
    }

    public static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType) {
        // 判断该类是否被该注解直接标记
        A annotation = clazz.getDeclaredAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        // 递归检索该类注解的注解中是否存在目标注解
        for (Annotation declaredAnn : clazz.getDeclaredAnnotations()) {
            Class<? extends Annotation> declaredType = declaredAnn.annotationType();
            // 进入递归
            annotation = findAnnotation(declaredType, annotationType);
            if (annotation != null) {
                return annotation;
            }

        }
        return null;
    }
}
