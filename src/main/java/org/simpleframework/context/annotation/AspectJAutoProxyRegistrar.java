package org.simpleframework.context.annotation;

import org.simpleframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;
import org.simpleframework.beans.factory.support.RootBeanDefinition;
import org.simpleframework.core.type.AnnotationMetadata;

/**
 * <h1>aop 功能开启</h1>
 * <p>注册 AnnotationAwareAspectJAutoProxyCreator 到 BeanDefinition</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-29 19:23:52
 */
public class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {

    public static final String AUTO_PROXY_CREATOR_BEAN_NAME = "aspectJAutoProxyCreator";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME)) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(AnnotationAwareAspectJAutoProxyCreator.class);
            registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
        }
    }
}
