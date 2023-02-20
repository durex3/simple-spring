package org.simpleframework.beans.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>属性值注入注解</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-20 13:19:38
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {

    String value();
}
