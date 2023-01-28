package org.simpleframework.aop.aspectj.annotation;

import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.AjTypeSystem;
import org.aspectj.lang.reflect.PerClauseKind;
import org.simpleframework.aop.Advisor;
import org.simpleframework.aop.aspectj.AspectJExpressionPointcut;
import org.simpleframework.aop.framework.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.simpleframework.util.AspectJUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>注解切面代理创建器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 11:08:15
 */
public class AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator {

    private final Map<String, List<Advisor>> advisorsCache = new ConcurrentHashMap<>();
    private List<String> aspectBeanNames;

    @Override
    protected List<Advisor> findCandidateAdvisors() {
        List<Advisor> advisors = super.findCandidateAdvisors();
        List<Advisor> buildAspectJAdvisors = buildAspectJAdvisors();
        if (buildAspectJAdvisors != null && !buildAspectJAdvisors.isEmpty()) {
            advisors.addAll(buildAspectJAdvisors);
        }
        return advisors;
    }

    public synchronized List<Advisor> buildAspectJAdvisors() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        if (this.aspectBeanNames == null) {
            return createAdvisors(beanFactory);
        }

        List<Advisor> advisors = new ArrayList<>();
        for (String aspectName : this.aspectBeanNames) {
            List<Advisor> cachedAdvisors = this.advisorsCache.get(aspectName);
            if (cachedAdvisors != null) {
                advisors.addAll(cachedAdvisors);
            } else {
                Class<?> aspectClass = beanFactory.getType(aspectName);
                advisors.addAll(getAdvisors(aspectClass, aspectName));
            }
        }
        return advisors;
    }

    public List<Advisor> getAdvisors(Class<?> aspectClass, String aspectName) {
        final List<Method> methods = new ArrayList<>();
        Method[] aspectMethods = aspectClass.getMethods();
        for (Method method : aspectMethods) {
            if (method.getAnnotation(Pointcut.class) == null) {
                methods.add(method);
            }
        }

        List<Advisor> advisors = new ArrayList<>();
        for (Method method : methods) {
            Advisor advisor = getAdvisor(aspectClass, method, aspectName);
            if (advisor != null) {
                advisors.add(advisor);
            }
        }
        return advisors;
    }


    @Override
    protected boolean isInfrastructureClass(Class<?> beanClass) {
        return (super.isInfrastructureClass(beanClass) || AspectJUtils.isAspect(beanClass));
    }

    private Advisor getAdvisor(Class<?> aspectClass, Method method, String aspectName) {
        Annotation annotation = AspectJUtils.findAspectJAnnotationOnMethod(method);
        if (annotation == null) {
            return null;
        }
        AspectJExpressionPointcut expressionPointcut = new AspectJExpressionPointcut(aspectClass, AspectJUtils.resolveExpression(annotation));
        expressionPointcut.setLocation(aspectName);
        return new InstantiationModelAwarePointcutAdvisorImpl(expressionPointcut, getBeanFactory(), method, aspectName);
    }

    private List<Advisor> createAdvisors(ConfigurableListableBeanFactory beanFactory) {
        this.aspectBeanNames = new ArrayList<>();
        List<Advisor> advisors = new ArrayList<>();
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Class<?> beanType = beanFactory.getType(beanName);
            if (beanType == null) {
                continue;
            }
            if (AspectJUtils.isAspect(beanType)) {
                aspectBeanNames.add(beanName);
                if (getAjType(beanType).getPerClause().getKind() == PerClauseKind.SINGLETON) {
                    List<Advisor> classAdvisors = getAdvisors(beanType, beanName);
                    if (beanFactory.isSingleton(beanName)) {
                        this.advisorsCache.put(beanName, classAdvisors);
                    }
                    advisors.addAll(classAdvisors);
                }
            }
        }
        return advisors;
    }

    private AjType<?> getAjType(Class<?> aspectClass) {
        AjType<?> ajType = null;
        Class<?> currClass = aspectClass;
        while (currClass != Object.class) {
            AjType<?> ajTypeToCheck = AjTypeSystem.getAjType(currClass);
            if (ajTypeToCheck.isAspect()) {
                ajType = ajTypeToCheck;
                break;
            }
            currClass = currClass.getSuperclass();
        }
        if (ajType == null) {
            throw new IllegalArgumentException(aspectClass.getName() + "is not an @AspectJ aspect");
        }
        return ajType;
    }

}
