package org.simpleframework.context.annotation;

import org.simpleframework.beans.factory.BeanCreationException;
import org.simpleframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinitionHolder;
import org.simpleframework.core.type.AnnotationMetadata;
import org.simpleframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
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
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        if (metadata.hasAnnotation(Component.class.getName())) {
            ConfigurationClass configurationClass = new ConfigurationClass(beanDefinition.getMetadata(), beanName);

            // 获取 @Import class
            if (metadata.hasAnnotation(Import.class.getName())) {
                Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(Import.class.getName());
                Class<?>[] values = (Class<?>[]) annotationAttributes.get("value");
                processImport(configurationClass, values);
            }

            // 获取 @Bean methods
            Method[] methods = beanClass.getDeclaredMethods();
            getBeanMethod(methods).forEach(configurationClass::addBeanMethod);

            result.add(configurationClass);
        }
    }

    private void processImport(ConfigurationClass configurationClass, Class<?>[] classes) {
        for (Class<?> clazz : classes) {
            if (ImportBeanDefinitionRegistrar.class.isAssignableFrom(clazz)) {
                if (clazz.isInterface()) {
                    throw new BeanCreationException(clazz.getName() + " Specified class is an interface");
                }
                try {
                    ImportBeanDefinitionRegistrar registrar = (ImportBeanDefinitionRegistrar)
                            clazz.getDeclaredConstructor().newInstance();
                    configurationClass.addImportBeanDefinitionRegistrar(registrar, configurationClass.getMetadata());
                } catch (Exception e) {
                    throw new BeanCreationException(clazz.getName() + " No suitable constructor found", e);
                }
            } else {
                // 普通的 @Import 导入
                // todo
            }
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
