package org.simpleframework.aop;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:34:54
 */
public interface TargetSource {

    Class<?> getTargetClass();

    Object getTarget();

}
