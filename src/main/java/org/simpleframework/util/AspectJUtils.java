package org.simpleframework.util;

import org.aspectj.lang.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 13:32:18
 */
public final class AspectJUtils {

    private static final String AJC_MAGIC = "ajc$";
    private static final String[] EXPRESSION_ATTRIBUTES = new String[]{"pointcut", "value"};
    private static final Class<?>[] ASPECTJ_ANNOTATION_CLASSES = new Class<?>[]{
            Pointcut.class, Around.class, Before.class, After.class, AfterReturning.class, AfterThrowing.class};

    private AspectJUtils() {

    }

    public static Annotation findAspectJAnnotationOnMethod(Method method) {
        for (Class<?> clazz : ASPECTJ_ANNOTATION_CLASSES) {
            Annotation[] methodAnnotations = method.getAnnotations();
            for (Annotation methodAnnotation : methodAnnotations) {
                if (methodAnnotation.annotationType() == clazz) {
                    return methodAnnotation;
                }
            }
        }
        return null;
    }

    public static boolean isAspect(Class<?> clazz) {
        return (AspectJUtils.hasAspectAnnotation(clazz) && !AspectJUtils.compiledByAjc(clazz));
    }

    public static String resolveExpression(Annotation annotation) {
        for (String attributeName : EXPRESSION_ATTRIBUTES) {
            Object val = AnnotationUtils.getValue(annotation, attributeName);
            if (val instanceof String) {
                String str = (String) val;
                if (!str.isEmpty()) {
                    return str;
                }
            }
        }
        throw new IllegalStateException("Failed to resolve expression: " + annotation);
    }

    private static boolean hasAspectAnnotation(Class<?> clazz) {
        return (AnnotationUtils.findAnnotation(clazz, Aspect.class) != null);
    }

    private static boolean compiledByAjc(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().startsWith(AJC_MAGIC)) {
                return true;
            }
        }
        return false;
    }
}
