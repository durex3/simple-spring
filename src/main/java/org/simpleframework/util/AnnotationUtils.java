package org.simpleframework.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            if (!isInJavaLangAnnotationPackage(declaredAnn))
                // 进入递归
                annotation = findAnnotation(declaredType, annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

    public static Object getValue(Annotation annotation, String attributeName) {
        if (annotation == null || StringUtils.isBlank(attributeName)) {
            return null;
        }
        try {
            Method method = annotation.annotationType().getDeclaredMethod(attributeName);
            ReflectionUtils.makeAccessible(method);
            return method.invoke(annotation);
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            return null;
        } catch (InvocationTargetException ex) {
            throw new IllegalStateException("Could not obtain value for annotation attribute '" +
                    attributeName + "' in " + annotation, ex);
        }
    }

    public static boolean isInJavaLangAnnotationPackage(Annotation annotation) {
        return (annotation != null && annotation.annotationType().getName().startsWith("java.lang.annotation"));
    }
}
