package org.simpleframework.beans.factory.annotation;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.BeanCreationException;
import org.simpleframework.beans.factory.BeanFactory;
import org.simpleframework.beans.factory.BeanFactoryAware;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.simpleframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>自动注入的 bean 后置处理器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-06 12:18:09
 */
public class AutowiredAnnotationBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;
    /**
     * 缓存注入信息元数据
     */
    private final Map<String, InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<>(256);

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void postProcessProperties(Object bean, String beanName) throws BeansException {
        InjectionMetadata injectionMetadata = findAutowiringMetadata(beanName, bean.getClass());
        try {
            injectionMetadata.inject(bean, beanName);
        } catch (Exception e) {
            throw new BeanCreationException(beanName + " Injection of autowired dependencies failed", e);
        }
    }

    private InjectionMetadata findAutowiringMetadata(String beanName, Class<?> clazz) {
        InjectionMetadata metadata;
        synchronized (this.injectionMetadataCache) {
            metadata = this.injectionMetadataCache.get(beanName);
            if (metadata == null) {
                metadata = buildAutowiringMetadata(clazz);
                this.injectionMetadataCache.put(beanName, metadata);
            }
        }
        return metadata;
    }

    private InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
        final List<InjectionMetadata.InjectedElement> currElements = new ArrayList<>();
        // 标记在字段上
        for (Field field : clazz.getDeclaredFields()) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                currElements.add(new AutowiredFieldElement(field, true));
            }
        }

        // 标记在方法上
        for (Method method : clazz.getDeclaredMethods()) {
            Autowired autowired = method.getAnnotation(Autowired.class);
            if (autowired != null) {
                currElements.add(new AutowiredMethodElement(method, false));
            }
        }

        return new InjectionMetadata(clazz, currElements);
    }

    private Object getValue(String beanName, Class<?> clazz) {
        Object value = beanFactory.getBean(beanName);
        if (value == null) {
            Map<String, ?> beans = beanFactory.getBeansOfType(clazz);
            if (beans.isEmpty()) {
                return null;
            }
            if (beans.size() > 1) {
                throw new BeanCreationException("expected single matching bean but found " + beans.size());
            }
            Collection<?> values = beans.values();
            value = values.iterator().next();
        }
        return value;
    }


    private class AutowiredFieldElement extends InjectionMetadata.InjectedElement {
        public AutowiredFieldElement(Member member, boolean isField) {
            super(member, isField);
        }

        @Override
        public void inject(Object bean, String beanName) throws IllegalAccessException {
            if (isField) {
                Field field = (Field) this.member;
                Object value = getValue(field.getName(), field.getDeclaringClass());
                if (value != null) {
                    ReflectionUtils.makeAccessible(field);
                    field.set(bean, value);
                }
            }
        }
    }

    private class AutowiredMethodElement extends InjectionMetadata.InjectedElement {
        public AutowiredMethodElement(Member member, boolean isField) {
            super(member, isField);
        }

        @Override
        public void inject(Object bean, String beanName) {
            if (!isField) {
                Method method = (Method) this.member;
                int argumentCount = method.getParameterCount();
                Object[] arguments = new Object[argumentCount];
                for (int i = 0; i < arguments.length; i++) {
                    String name = method.getParameters()[i].getName();
                    Class<?> type = method.getParameters()[i].getType();
                    Object value = getValue(name, type);
                    if (value == null) {
                        throw new BeanCreationException("No qualifying bean of type " + type);
                    }
                    arguments[i] = value;
                }
                ReflectionUtils.makeAccessible(method);
                ReflectionUtils.invokeMethod(method, bean, arguments);
            }
        }
    }
}
