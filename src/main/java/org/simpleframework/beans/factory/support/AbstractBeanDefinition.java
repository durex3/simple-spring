package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.config.BeanDefinition;

/**
 * <h1>抽象 bean 的定义信息</h1>
 *
 * @Author: liugelong
 * @createTime: 2022-12-30 23:53:27
 * @version: 1.0
 */
public abstract class AbstractBeanDefinition implements BeanDefinition {

    public static final String SCOPE_DEFAULT = "";

    private Object beanClass;

    private String scope = SCOPE_DEFAULT;

    private Boolean lazyInit;

    protected AbstractBeanDefinition() {

    }

    protected AbstractBeanDefinition(BeanDefinition original) {
        setBeanClassName(original.getBeanClassName());
        setScope(original.getScope());
        if (original instanceof AbstractBeanDefinition originalAbd) {
            if (originalAbd.hasBeanClass()) {
                setBeanClass(originalAbd.getBeanClass());
            }
        }
        if (lazyInit != null) {
            setLazyInit(original.isLazyInit());
        }
    }

    /**
     * <h2>克隆这个 bean 定义。</h2>
     *
     * @return 定义信息
     */
    public abstract AbstractBeanDefinition cloneBeanDefinition();

    @Override
    public Object clone() {
        return cloneBeanDefinition();
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClass = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        Object beanClassObject = this.beanClass;
        if (beanClassObject instanceof Class) {
            return ((Class<?>) beanClassObject).getName();
        } else {
            return (String) beanClassObject;
        }
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public boolean isLazyInit() {
        return (this.lazyInit != null && this.lazyInit);
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() throws IllegalStateException {
        Object beanClassObject = this.beanClass;
        if (beanClassObject == null) {
            throw new IllegalStateException("No bean class specified on bean definition");
        }
        if (!(beanClassObject instanceof Class)) {
            throw new IllegalStateException(
                    "Bean class name [" + beanClassObject + "] has not been resolved into an actual Class");
        }
        return (Class<?>) beanClassObject;
    }

    public boolean hasBeanClass() {
        return (this.beanClass instanceof Class);
    }
}