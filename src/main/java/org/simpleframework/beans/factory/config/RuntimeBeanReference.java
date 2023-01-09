package org.simpleframework.beans.factory.config;

/**
 * <h1>用于属性值对象，当它是对工厂中一个 Bean 的引用时，将在运行时解析</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 20:38:14
 */
public class RuntimeBeanReference implements BeanReference {

    private final String beanName;

    private final Class<?> beanType;

    private Object source;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
        this.beanType = null;
    }

    public RuntimeBeanReference(Class<?> beanType) {
        this.beanName = beanType.getName();
        this.beanType = beanType;
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    public Class<?> getBeanType() {
        return this.beanType;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return this.source;
    }
}
