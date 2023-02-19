package org.simpleframework.context.annotation;

import org.simpleframework.core.io.support.PropertySourceFactory;

/**
 * <h1>注解的方添加配置文件</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-18 13:06:36
 */
public @interface PropertySource {

    String name() default "";

    String[] value();

    Class<? extends PropertySourceFactory> factory() default PropertySourceFactory.class;
}
