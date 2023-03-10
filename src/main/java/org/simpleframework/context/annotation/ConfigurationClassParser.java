package org.simpleframework.context.annotation;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.BeanCreationException;
import org.simpleframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinitionHolder;
import org.simpleframework.core.env.Environment;
import org.simpleframework.core.io.ResourceLoader;
import org.simpleframework.core.io.support.DefaultPropertySourceFactory;
import org.simpleframework.core.io.support.PropertySourceFactory;
import org.simpleframework.core.type.AnnotationMetadata;
import org.simpleframework.stereotype.Component;
import org.simpleframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * <h1>@Configuration 注解解析</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-18 21:43:18
 */
public class ConfigurationClassParser {

    private static final PropertySourceFactory DEFAULT_PROPERTY_SOURCE_FACTORY = new DefaultPropertySourceFactory();
    private final List<String> propertySourceNames = new ArrayList<>();
    private final ResourceLoader resourceLoader;
    private final Environment environment;


    public ConfigurationClassParser(ResourceLoader resourceLoader, Environment environment) {
        this.resourceLoader = resourceLoader;
        this.environment = environment;
    }


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

            // 获取 @PropertySource class
            if (metadata.hasAnnotation(PropertySource.class.getName())) {
                processPropertySource(metadata);
            }

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

    private void processPropertySource(AnnotationMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(PropertySource.class.getName());
        String name = annotationAttributes.get("name").toString();
        String[] locations = (String[]) annotationAttributes.get("value");
        Class<? extends PropertySourceFactory> factoryClass = (Class<? extends PropertySourceFactory>) annotationAttributes.get("factory");
        PropertySourceFactory factory = (factoryClass == PropertySourceFactory.class ? DEFAULT_PROPERTY_SOURCE_FACTORY : ClassUtils.instantiateClass(factoryClass));
        for (String location : locations) {
            try {
                addPropertySource(factory.createPropertySource(name, resourceLoader.getResource(location)));
            } catch (Exception e) {
                throw new BeansException("Properties location [" + location + "] not resolvable: ", e);
            }
        }
    }

    private void addPropertySource(org.simpleframework.core.env.PropertySource<?> propertySource) {
        List<org.simpleframework.core.env.PropertySource<?>> propertySources = environment.getPropertySources();
        if (StringUtils.isNotBlank(propertySource.getName()) && !propertySourceNames.contains(propertySource.getName())) {
            propertySources.add(propertySource);
            propertySourceNames.add(propertySource.getName());
        }
        propertySources.add(propertySource);
    }

    private void processImport(ConfigurationClass configurationClass, Class<?>[] classes) {
        for (Class<?> clazz : classes) {
            if (ImportBeanDefinitionRegistrar.class.isAssignableFrom(clazz)) {
                if (clazz.isInterface()) {
                    throw new BeanCreationException(clazz.getName() + " Specified class is an interface");
                }
                try {
                    ImportBeanDefinitionRegistrar registrar = (ImportBeanDefinitionRegistrar) clazz.getDeclaredConstructor().newInstance();
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
