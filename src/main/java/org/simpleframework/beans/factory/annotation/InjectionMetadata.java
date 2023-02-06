package org.simpleframework.beans.factory.annotation;

import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Collection;

/**
 * <h1>自动注入信息元数据</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-06 13:05:24
 */
public class InjectionMetadata {

    private final Class<?> targetClass;
    private final Collection<InjectedElement> injectedElements;

    public abstract static class InjectedElement {

        protected final Member member;

        protected final boolean isField;

        public InjectedElement(Member member, boolean isField) {
            this.member = member;
            this.isField = isField;
        }

        public void inject(Object bean, String beanName) throws IllegalAccessException {

        }
    }

    public InjectionMetadata(Class<?> targetClass, Collection<InjectedElement> injectedElements) {
        this.targetClass = targetClass;
        this.injectedElements = injectedElements;
    }

    public void inject(Object bean, String beanName) throws Exception {
        Collection<InjectedElement> elements = this.injectedElements;
        if (!elements.isEmpty()) {
            for (InjectedElement element : elements) {
                element.inject(bean, beanName);
            }
        }
    }


    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Collection<InjectedElement> getInjectedElements() {
        return injectedElements;
    }
}
