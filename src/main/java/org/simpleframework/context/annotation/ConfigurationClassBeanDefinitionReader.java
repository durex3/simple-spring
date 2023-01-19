package org.simpleframework.context.annotation;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.beans.factory.BeanDefinitionStoreException;
import org.simpleframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-18 21:46:05
 */
public class ConfigurationClassBeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    public ConfigurationClassBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
        for (ConfigurationClass configClass : configurationModel) {
            loadBeanDefinitionsForConfigurationClass(configClass);
        }
    }

    /**
     * <h2>加载 配置类 @Configuration 的 @Bean 定义信息</h2>
     *
     * @param configClass 配置类的信息
     */
    private void loadBeanDefinitionsForConfigurationClass(ConfigurationClass configClass) {
        for (Method beanMethod : configClass.getBeanMethods()) {
            loadBeanDefinitionsForBeanMethod(configClass, beanMethod);
        }
    }

    private void loadBeanDefinitionsForBeanMethod(ConfigurationClass configClass, Method beanMethod) {
        if (beanMethod.getReturnType() == Void.class) {
            throw new BeanDefinitionStoreException("@Bean method return type is void");
        }
        AnnotatedGenericBeanDefinition beanDef = new AnnotatedGenericBeanDefinition(beanMethod.getReturnType());
        String beanName;
        Bean bean = beanMethod.getAnnotation(Bean.class);
        beanName = bean.value();
        if (beanName.isEmpty()) {
            beanName = Introspector.decapitalize(beanMethod.getName());
        }

        beanDef.setFactoryBeanName(configClass.getBeanName());
        beanDef.setFactoryMethodName(beanMethod.getName());

        String initMethodName = bean.initMethod();
        if (StringUtils.isNotBlank(initMethodName)) {
            beanDef.setInitMethodName(initMethodName);
        }

        String destroyMethodName = bean.destroyMethod();
        if (StringUtils.isNotBlank(destroyMethodName)) {
            beanDef.setDestroyMethodName(destroyMethodName);
        }
        registry.registerBeanDefinition(beanName, beanDef);
    }
}
