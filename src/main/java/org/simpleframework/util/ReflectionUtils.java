package org.simpleframework.util;

import java.lang.reflect.*;

/**
 * <h1>反射工具类</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 20:47:01
 */
public final class ReflectionUtils {

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    private ReflectionUtils() {
    }

    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, EMPTY_OBJECT_ARRAY);
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception ex) {
            handleReflectionException(ex);
        }
        throw new IllegalStateException("Should never get here");
    }

    public static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException e) {
            handleInvocationTargetException(e);
        }
        if (ex instanceof RuntimeException e) {
            throw e;
        }
        throw new UndeclaredThrowableException(ex);
    }

    public static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException e) {
            throw e;
        }
        if (ex instanceof Error e) {
            throw e;
        }
        throw new UndeclaredThrowableException(ex);
    }

    public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            makeAccessible(field);
            field.set(object, fieldValue);
        } catch (Exception ex) {
            handleReflectionException(ex);
        }
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

}
