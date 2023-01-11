package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.MutablePropertyValues;
import org.simpleframework.beans.PropertyValues;
import org.simpleframework.beans.factory.config.BeanReference;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * <h1>抽象的 bean 工厂，能够实现自动装配</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 12:36:43
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, RootBeanDefinition mbd, Object... args) {
        Object bean;
        try {
            bean = createBeanInstance(beanName, mbd, args);
            populateBean(beanName, mbd, bean);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        return bean;
    }

    /**
     * <h2>填充 bean 属性</h2>
     *
     * @param beanName bean 名字
     * @param mbd      bean 定义信息
     * @param bean     bean 实例
     */
    protected void populateBean(String beanName, RootBeanDefinition mbd, Object bean) {
        if (mbd.hasPropertyValues()) {
            applyPropertyValues(beanName, bean, mbd.getPropertyValues());
        }
    }

    /**
     * <h2>bean 属性赋值</h2>
     *
     * @param beanName bean 名字
     * @param bean     bean 实例
     * @param pvs      bean 属性
     */
    protected void applyPropertyValues(String beanName, Object bean, PropertyValues pvs) {
        if (pvs instanceof MutablePropertyValues) {
            try {
                pvs.stream().forEach(mpv -> {
                    String name = mpv.getName();
                    Object value = mpv.getValue();

                    if (value instanceof BeanReference) {
                        // A 依赖 B，获取 B 的实例化
                        String beanReferenceName = ((BeanReference) value).getBeanName();
                        value = getBean(beanReferenceName);
                    }
                    // 属性填充
                    ReflectionUtils.setFieldValue(bean, name, value);
                });
            } catch (Exception e) {
                throw new BeansException("Error setting property values: " + beanName, e);
            }
        }
    }


    protected Object createBeanInstance(String beanName, RootBeanDefinition mbd, Object... args) {
        Class<?> beanClass = mbd.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        if (declaredConstructors.length == 0) {
            throw new BeansException(beanName + " No default constructor found");
        }
        Constructor<?> constructorToUse = Arrays.stream(declaredConstructors)
                .filter(ctor -> (args == null && ctor.getParameterCount() == 0)
                        || (args != null && ctor.getParameterTypes().length == args.length))
                .findFirst()
                .orElseThrow(() -> new BeansException(beanName + " Illegal arguments for constructor"));

        return instantiationStrategy.instantiate(mbd, beanName, constructorToUse, args);
    }
}
