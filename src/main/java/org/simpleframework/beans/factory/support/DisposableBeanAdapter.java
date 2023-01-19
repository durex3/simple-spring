package org.simpleframework.beans.factory.support;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.DisposableBean;
import org.simpleframework.util.ClassUtils;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * <h1>销毁接口适配器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-18 20:04:31
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private Method destroyMethod;

    public DisposableBeanAdapter(Object bean, String beanName, RootBeanDefinition beanDefinition) {
        this.bean = bean;
        String destroyMethodName = beanDefinition.getDestroyMethodName();
        if (StringUtils.isNotBlank(beanDefinition.getDestroyMethodName())) {
            this.destroyMethod = ClassUtils.getMethodIfAvailable(bean.getClass(), destroyMethodName);
        }
        if (this.destroyMethod == null) {
            throw new BeansException("Could not find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
        }
    }

    @Override
    public void destroy() throws Exception {
        boolean isDisposableBean = this.bean instanceof DisposableBean;
        if (isDisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (this.destroyMethod != null && !(isDisposableBean && "destroy".equals(destroyMethod.getName()))) {
            invokeCustomDestroyMethod(this.destroyMethod);
        }
    }

    private void invokeCustomDestroyMethod(Method destroyMethod) {
        ReflectionUtils.makeAccessible(destroyMethod);
        ReflectionUtils.invokeMethod(destroyMethod, this.bean);
    }
}
