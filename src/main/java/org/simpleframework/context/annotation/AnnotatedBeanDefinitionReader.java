package org.simpleframework.context.annotation;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.simpleframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;
import org.simpleframework.beans.factory.support.RootBeanDefinition;
import org.simpleframework.util.ClassUtils;

import java.beans.Introspector;
import java.lang.annotation.Annotation;

/**
 * <h1>注解方式注册 bean 读取器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 19:16:35
 */
public class AnnotatedBeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void register(Class<?>... componentClasses) {
        for (Class<?> componentClass : componentClasses) {
            registerBean(componentClass);
        }
        registerAnnotationConfigProcessors(registry);
    }

    public void registerBean(Class<?> beanClass) {
        doRegisterBean(beanClass, null, null);
    }

    public void registerBean(Class<?> beanClass, String name) {
        doRegisterBean(beanClass, name, null);
    }

    public <T> void registerBean(Class<T> beanClass, Class<? extends Annotation>[] qualifiers) {
        doRegisterBean(beanClass, null, qualifiers);
    }

    public <T> void registerBean(Class<T> beanClass, String name, Class<? extends Annotation>[] qualifiers) {
        doRegisterBean(beanClass, name, qualifiers);
    }

    protected void registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
        RootBeanDefinition classPostProcessor = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
        registry.registerBeanDefinition(classPostProcessor.getBeanClassName(), classPostProcessor);
        RootBeanDefinition autowiredPostProcessor = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
        registry.registerBeanDefinition(autowiredPostProcessor.getBeanClassName(), autowiredPostProcessor);
    }

    /**
     * <h2>从给定的 Bean 类注册一个 Bean</h2>
     *
     * @param beanClass  类型
     * @param name       bean 名字
     * @param qualifiers 条件注解 @Primary @Lazy 等
     */
    private <T> void doRegisterBean(Class<T> beanClass, String name, Class<? extends Annotation>[] qualifiers) {
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
        String beanName;
        if (StringUtils.isNotBlank(name)) {
            beanName = name;
        } else {
            String shortClassName = ClassUtils.getShortName(abd.getBeanClassName());
            beanName = Introspector.decapitalize(shortClassName);
        }
        registry.registerBeanDefinition(beanName, abd);
    }

}
