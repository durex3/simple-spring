package org.simpleframework.beans.factory.annotation;

import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.core.type.AnnotationMetadata;

/**
 * <h1>注解的 bean 定义信息</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 20:52:06
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

    /**
     * <h2>获取注解元数据信息</h2>
     *
     * @return {@link AnnotationMetadata}
     */
    AnnotationMetadata getMetadata();

}
