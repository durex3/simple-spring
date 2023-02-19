package org.simpleframework.context.annotation;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinitionHolder;
import org.simpleframework.beans.factory.config.BeanDefinitionRegistryPostProcessor;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;
import org.simpleframework.context.EnvironmentAware;
import org.simpleframework.core.env.Environment;
import org.simpleframework.core.io.DefaultResourceLoader;
import org.simpleframework.core.io.ResourceLoader;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-18 21:41:13
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private ConfigurationClassBeanDefinitionReader reader;
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // Parse @Configuration class
        ConfigurationClassParser parser = new ConfigurationClassParser(resourceLoader, environment);
        String[] beanNames = registry.getBeanDefinitionNames();
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>(beanNames.length);
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if (beanDefinition instanceof AnnotatedGenericBeanDefinition) {
                AnnotatedGenericBeanDefinition bd = (AnnotatedGenericBeanDefinition) beanDefinition;
                beanDefinitions.add(new BeanDefinitionHolder(beanName, bd.getBeanClass(), bd));
            }
        }
        Set<ConfigurationClass> configurationClasses = parser.parse(beanDefinitions);
        if (this.reader == null) {
            this.reader = new ConfigurationClassBeanDefinitionReader(registry);
        }
        this.reader.loadBeanDefinitions(configurationClasses);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
