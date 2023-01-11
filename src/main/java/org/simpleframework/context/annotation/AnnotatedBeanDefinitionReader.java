package org.simpleframework.context.annotation;

import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.function.Supplier;

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
    }

    public void registerBean(Class<?> beanClass) {
        doRegisterBean(beanClass, null, null);
    }

    /**
     * <h2>从给定的 Bean 类注册一个 Bean</h2>
     *
     * @param beanClass 类型
     * @param name      bean 名字
     * @param supplier  回调
     */
    private <T> void doRegisterBean(Class<T> beanClass, String name, Supplier<T> supplier) {

    }

}
