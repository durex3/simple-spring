package org.simpleframework.context.annotation;

import org.simpleframework.beans.factory.config.ConfigurableBeanFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>bean的作用域</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-21 18:38:12
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    /**
     * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON
     */
    String value() default "";
}
