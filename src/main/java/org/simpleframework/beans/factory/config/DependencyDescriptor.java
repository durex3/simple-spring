package org.simpleframework.beans.factory.config;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-20 14:58:08
 */
public class DependencyDescriptor {

    private String targetBeanName;

    private String targetValue;

    private Class<?> targetBeanClass;

    public String getTargetBeanName() {
        return targetBeanName;
    }

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public Class<?> getTargetBeanClass() {
        return targetBeanClass;
    }

    public void setTargetBeanClass(Class<?> targetBeanClass) {
        this.targetBeanClass = targetBeanClass;
    }
}
