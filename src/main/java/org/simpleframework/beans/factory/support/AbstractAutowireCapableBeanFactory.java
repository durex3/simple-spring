package org.simpleframework.beans.factory.support;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.MutablePropertyValues;
import org.simpleframework.beans.PropertyValues;
import org.simpleframework.beans.factory.BeanCreationException;
import org.simpleframework.beans.factory.DisposableBean;
import org.simpleframework.beans.factory.InitializingBean;
import org.simpleframework.beans.factory.NoSuchBeanDefinitionException;
import org.simpleframework.beans.factory.config.AutowireCapableBeanFactory;
import org.simpleframework.beans.factory.config.BeanPostProcessor;
import org.simpleframework.beans.factory.config.BeanReference;
import org.simpleframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.simpleframework.util.ClassUtils;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <h1>抽象的 bean 工厂，能够实现自动装配</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 12:36:43
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, RootBeanDefinition mbd, Object... args) {
        Object bean;
        try {
            bean = resolveBeforeInstantiation(beanName, mbd);
            if (bean != null) {
                return bean;
            }
            bean = createBeanInstance(beanName, mbd, args);
            populateBean(beanName, mbd, bean);
            bean = initializeBean(beanName, bean, mbd);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        return bean;
    }

    protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
        Object bean;
        bean = applyBeanPostProcessorsBeforeInstantiation(mbd.getBeanClass(), beanName);
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor bp : getBeanPostProcessors()) {
            if (bp instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    protected void invokeInitMethods(String beanName, Object bean, RootBeanDefinition mbd) throws Exception {
        boolean isInitializingBean = (bean instanceof InitializingBean);

        if (isInitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        if (mbd != null && bean.getClass() != null) {
            String initMethodName = mbd.getInitMethodName();
            if (StringUtils.isNotBlank(initMethodName) && !(isInitializingBean && "afterPropertiesSet".equals(initMethodName))) {
                invokeCustomInitMethod(beanName, bean, mbd);
            }
        }
    }

    protected void invokeCustomInitMethod(String beanName, Object bean, RootBeanDefinition mbd) {
        String initMethodName = mbd.getInitMethodName();
        if (StringUtils.isBlank(initMethodName)) {
            throw new IllegalStateException("No init method set");
        }

        Method initMethod = ClassUtils.getMethodIfAvailable(bean.getClass(), initMethodName);
        if (initMethod == null) {
            throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
        }

        ReflectionUtils.makeAccessible(initMethod);
        ReflectionUtils.invokeMethod(initMethod, bean);
    }

    @Override
    public Object initializeBean(Object existingBean, String beanName) throws BeansException {
        return initializeBean(beanName, existingBean, null);
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * <h2>填充 bean 属性</h2>
     *
     * @param beanName bean 名字
     * @param mbd      bean 定义信息
     * @param bean     bean 实例
     */
    protected void populateBean(String beanName, RootBeanDefinition mbd, Object bean) {
        if (hasInstantiationAwareBeanPostProcessors()) {
            for (BeanPostProcessor bp : getBeanPostProcessors()) {
                if (bp instanceof InstantiationAwareBeanPostProcessor) {
                    InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                    if (!ibp.postProcessAfterInstantiation(bean, beanName)) {
                        return;
                    }
                }
            }
        }

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

    protected Object initializeBean(String beanName, Object bean, RootBeanDefinition mbd) {
        Object wrappedBean = bean;

        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);

        try {
            // 执行初始化方法
            invokeInitMethods(beanName, wrappedBean, mbd);
        } catch (Exception ex) {
            throw new BeanCreationException(beanName + " Invocation of init method failed", ex);
        }

        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

        try {
            // 注册销毁方法
            registerDisposableBeanIfNecessary(beanName, bean, mbd);
        } catch (BeansException ex) {
            throw new BeansException(beanName + " register disposable", ex);
        }

        return wrappedBean;
    }

    protected Object createBeanInstance(String beanName, RootBeanDefinition mbd, Object... args) {
        if (mbd.getFactoryMethodName() != null) {
            return instantiateUsingFactoryMethod(beanName, mbd, args);
        }
        Class<?> beanClass = mbd.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        if (declaredConstructors.length == 0) {
            throw new BeansException(beanName + " No default constructor found");
        }
        Constructor<?> constructorToUse = Arrays.stream(declaredConstructors)
                .filter(ctor -> (args == null && ctor.getParameterCount() == 0) || (args != null && ctor.getParameterTypes().length == args.length))
                .findFirst()
                .orElseThrow(() -> new BeansException(beanName + " Illegal arguments for constructor"));

        return instantiationStrategy.instantiate(mbd, beanName, constructorToUse, args);
    }

    protected Object instantiateUsingFactoryMethod(String beanName, RootBeanDefinition mbd, Object[] args) {
        Object factoryBean = this.getBean(mbd.getFactoryBeanName());
        Method[] methods = factoryBean.getClass().getDeclaredMethods();
        Method factoryMethod = null;
        for (Method method : methods) {
            if (mbd.getFactoryMethodName().equals(method.getName()) && (args == null || args.length == method.getParameterCount())) {
                factoryMethod = method;
                break;
            }
        }
        if (factoryMethod == null) {
            throw new NoSuchBeanDefinitionException(beanName + " no such method " + mbd.getFactoryMethodName());
        }
        return instantiationStrategy.instantiate(mbd, beanName, factoryBean, factoryMethod, args);
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, RootBeanDefinition mbd) {
        if (mbd.isSingleton() && bean instanceof DisposableBean) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, mbd));
        }
    }
}
