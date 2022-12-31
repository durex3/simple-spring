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
        setLazyInit(original.isLazyInit());
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
        return lazyInit;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
}