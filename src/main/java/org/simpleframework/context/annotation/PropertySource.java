package org.simpleframework.context.annotation;

import org.simpleframework.core.io.support.PropertySourceFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>注解的方添加配置文件</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-18 13:06:36
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySource {

    String name() default "";

    String[] value();

    Class<? extends PropertySourceFactory> factory() default PropertySourceFactory.class;
}
