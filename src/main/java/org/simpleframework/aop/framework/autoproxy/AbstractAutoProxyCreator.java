package org.simpleframework.aop.framework.autoproxy;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.aop.*;
import org.simpleframework.aop.aspectj.annotation.InstantiationModelAwarePointcutAdvisorImpl;
import org.simpleframework.aop.framework.ProxyFactory;
import org.simpleframework.aop.target.SingletonTargetSource;
import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.BeanFactory;
import org.simpleframework.beans.factory.BeanFactoryAware;
import org.simpleframework.beans.factory.FactoryBean;
import org.simpleframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>抽象的代理创建器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 11:02:39
 */
public abstract class AbstractAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;
    private final Map<Object, Boolean> advisedBeans = new ConcurrentHashMap<>(256);

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        if (shouldSkip(candidateAdvisors, beanName) || isInfrastructureClass(beanClass)) {
            Object cacheKey = getCacheKey(beanClass, beanName);
            advisedBeans.put(cacheKey, Boolean.FALSE);
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            return wrapIfNecessary(bean, beanName, getCacheKey(bean.getClass(), beanName).toString());
        }
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected boolean shouldSkip(List<Advisor> candidateAdvisors, String beanName) {
        for (Advisor advisor : candidateAdvisors) {
            if (advisor instanceof InstantiationModelAwarePointcutAdvisorImpl
                    && ((InstantiationModelAwarePointcutAdvisorImpl) advisor).getAspectName().equals(beanName)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    protected Object getCacheKey(Class<?> beanClass, String beanName) {
        if (StringUtils.isNotBlank(beanName)) {
            return (FactoryBean.class.isAssignableFrom(beanClass) ? BeanFactory.FACTORY_BEAN_PREFIX + beanName : beanName);
        } else {
            return beanClass;
        }
    }

    protected Object wrapIfNecessary(Object bean, String beanName, String cacheKey) {
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        if (candidateAdvisors == null || candidateAdvisors.isEmpty()) {
            return bean;
        }

        if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
            return bean;
        }

        List<Advisor> advisors = new ArrayList<>(candidateAdvisors.size());
        for (Advisor advisor : candidateAdvisors) {
            if (advisor instanceof PointcutAdvisor) {
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                Pointcut pc = pointcutAdvisor.getPointcut();
                if (pc.getClassFilter().matches(bean.getClass())) {
                    advisors.add(advisor);
                }
            }
        }

        if (!advisors.isEmpty()) {
            return createProxy(advisors, new SingletonTargetSource(bean));
        }

        return bean;
    }

    protected Object createProxy(List<Advisor> advisors, TargetSource targetSource) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetSource(targetSource);
        proxyFactory.setAdvisors(advisors);
        return proxyFactory.getProxy();
    }

    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    protected abstract List<Advisor> findCandidateAdvisors();
}
