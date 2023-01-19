package org.simpleframework.context.annotation;

import org.simpleframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinitionHolder;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-18 21:43:18
 */
public class ConfigurationClassParser {

    public Set<ConfigurationClass> parse(Set<BeanDefinitionHolder> beanDefinitions) {
        Set<ConfigurationClass> result = new LinkedHashSet<>();
        for (BeanDefinitionHolder holder : beanDefinitions) {
            BeanDefinition bd = holder.getBeanDefinition();
            if (bd instanceof AnnotatedBeanDefinition) {
                parse(result, ((AnnotatedBeanDefinition) bd), holder.getBeanName());
            }
        }
        return result;
    }

    private void parse(Set<ConfigurationClass> result, AnnotatedBeanDefinition beanDefinition, String beanName) {
        Class<?> beanClass;
        try {
            beanClass = Class.forName(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(beanName + " No bean class specified on bean definition");
        }
        Set<String> types = beanDefinition.getMetadata().getAnnotationTypes();
        if (types.contains("org.simpleframework.context.annotation.Configuration")) {
            ConfigurationClass configurationClass = new ConfigurationClass(beanDefinition.getMetadata(), beanName);
            // 获取 @Bean methods
            Method[] methods = beanClass.getDeclaredMethods();
            getBeanMethod(methods).forEach(configurationClass::addBeanMethod);
            result.add(configurationClass);
        }
    }

    private Set<Method> getBeanMethod(Method[] methods) {
        Set<Method> result = new LinkedHashSet<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Bean.class)) {
                result.add(method);
            }
        }
        return result;
    }
}
