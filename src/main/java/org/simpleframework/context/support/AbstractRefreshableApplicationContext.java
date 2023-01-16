package org.simpleframework.context.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.simpleframework.beans.factory.support.DefaultListableBeanFactory;
import org.simpleframework.context.ApplicationContextException;

import java.io.IOException;

/**
 * <h1>提供可刷新(创建、销毁) BeanFactory 的功能</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 11:33:49
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected final void refreshBeanFactory() throws BeansException {
        try {
            DefaultListableBeanFactory defaultListableBeanFactory = createBeanFactory();
            loadBeanDefinitions(defaultListableBeanFactory);
            this.beanFactory = defaultListableBeanFactory;
        } catch (IOException ex) {
            throw new ApplicationContextException("I/O error parsing bean definition source for " + getId(), ex);
        }
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        DefaultListableBeanFactory defaultListableBeanFactory = this.beanFactory;
        if (beanFactory == null) {
            throw new IllegalStateException("BeanFactory not initialized or already closed - " +
                    "call 'refresh' before accessing beans via the ApplicationContext");
        }
        return defaultListableBeanFactory;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected final boolean hasBeanFactory() {
        return (this.beanFactory != null);
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
            throws BeansException, IOException;
}
