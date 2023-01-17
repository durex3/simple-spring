package org.simpleframework.context.support;

import org.simpleframework.beans.factory.support.DefaultListableBeanFactory;
import org.simpleframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.simpleframework.context.annotation.AnnotationConfigRegistry;
import org.simpleframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * <h1>基于注解配置的应用上下文</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-17 19:17:07
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

    private final AnnotatedBeanDefinitionReader reader;

    private final ClassPathBeanDefinitionScanner scanner;

    public AnnotationConfigApplicationContext() {
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }

    public AnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }

    public AnnotationConfigApplicationContext(String... basePackages) {
        this();
        scan(basePackages);
        refresh();
    }

    public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
        this();
        register(componentClasses);
        refresh();
    }

    @Override
    public void register(Class<?>... componentClasses) {
        if (componentClasses == null || componentClasses.length == 0) {
            throw new IllegalArgumentException("At least one component class must be specified");
        }
        this.reader.register(componentClasses);
    }

    @Override
    public void scan(String... basePackages) {
        if (basePackages == null || basePackages.length == 0) {
            throw new IllegalArgumentException("At least one base package must be specified");
        }
        this.scanner.scan(basePackages);
    }
}
