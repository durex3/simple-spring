package org.simpleframework.context.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.BeanPostProcessor;
import org.simpleframework.context.ApplicationContextAware;
import org.simpleframework.context.ConfigurableApplicationContext;
import org.simpleframework.context.EnvironmentAware;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-20 19:35:03
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ConfigurableApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof ApplicationContextAware)) {
            return bean;
        }

        invokeAwareInterfaces(bean);

        return bean;
    }

    private void invokeAwareInterfaces(Object bean) {
        if (bean instanceof EnvironmentAware) {
            ((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
        }
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
        }
    }
}
