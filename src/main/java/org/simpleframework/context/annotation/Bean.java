package org.simpleframework.context.annotation;

import java.lang.annotation.*;

/**
 * <h1>在配置类上面的 bean</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-19 14:23:51
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {

    String value() default "";

    String initMethod() default "";

    String destroyMethod() default "";
}
