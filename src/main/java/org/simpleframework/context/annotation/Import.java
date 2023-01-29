package org.simpleframework.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>导入组件 配合 {@link Configuration @Configuration}</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-29 11:22:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Import {

    Class<?>[] value();
}
