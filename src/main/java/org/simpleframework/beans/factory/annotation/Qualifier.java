package org.simpleframework.beans.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>配合{@link Autowired}使用</h1>
 * <p>指定要注入的 bean name</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-13 12:55:35
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {

    String value() default "";
}
