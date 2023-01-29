package org.simpleframework.context.annotation;

import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;
import org.simpleframework.core.type.AnnotationMetadata;

/**
 * <h1>Import 方式的 bean 定义信息注册器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @see Import
 * @since 1.0 2023-01-29 13:47:18
 */
public interface ImportBeanDefinitionRegistrar {

    /**
     * <h2>注册 bean 的定义信息</h2>
     *
     * @param importingClassMetadata 注解元数据
     * @param registry               注册中心
     */
    default void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    }
}
