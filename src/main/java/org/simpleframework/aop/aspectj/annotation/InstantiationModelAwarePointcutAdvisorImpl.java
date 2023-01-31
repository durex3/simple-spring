package org.simpleframework.aop.aspectj.annotation;

import org.simpleframework.aop.Advice;
import org.simpleframework.aop.Pointcut;
import org.simpleframework.aop.PointcutAdvisor;
import org.simpleframework.aop.aspectj.*;
import org.simpleframework.beans.factory.BeanFactory;
import org.simpleframework.util.AspectJUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * <h1>切点增强器实现</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 13:19:09
 */
public class InstantiationModelAwarePointcutAdvisorImpl implements PointcutAdvisor {

    private final Pointcut pointcut;
    private final BeanFactory beanFactory;
    private final Method aspectJAdviceMethod;
    private final String aspectName;
    private Advice instantiatedAdvice;

    private static class AspectJAnnotationType {
        public static final String BEFORE = "org.aspectj.lang.annotation.Before";
        public static final String AFTER_RETURNING = "org.aspectj.lang.annotation.AfterReturning";
        public static final String AFTER_THROWING = "org.aspectj.lang.annotation.AfterThrowing";
        public static final String AROUND = "org.aspectj.lang.annotation.Around";
    }

    public InstantiationModelAwarePointcutAdvisorImpl(AspectJExpressionPointcut pointcut, BeanFactory beanFactory, Method aspectJAdviceMethod, String aspectName) {
        this.pointcut = pointcut;
        this.aspectJAdviceMethod = aspectJAdviceMethod;
        this.aspectName = aspectName;
        this.beanFactory = beanFactory;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        if (this.instantiatedAdvice == null) {
            this.instantiatedAdvice = instantiateAdvice();
        }
        return this.instantiatedAdvice;
    }

    public Method getAspectJAdviceMethod() {
        return aspectJAdviceMethod;
    }

    protected Advice instantiateAdvice() {
        Annotation aspectJAnnotation = AspectJUtils.findAspectJAnnotationOnMethod(this.aspectJAdviceMethod);
        if (aspectJAnnotation == null) {
            return null;
        }

        AbstractAspectJAdvice advice;
        switch (aspectJAnnotation.annotationType().getName()) {
            case AspectJAnnotationType.BEFORE:
                advice = new AspectJMethodBeforeAdvice(beanFactory.getBean(aspectName), aspectJAdviceMethod);
                break;
            case AspectJAnnotationType.AFTER_RETURNING:
                advice = new AspectJAfterReturningAdvice(beanFactory.getBean(aspectName), aspectJAdviceMethod);
                break;
            case AspectJAnnotationType.AFTER_THROWING:
                advice = new AspectJAfterThrowingAdvice(beanFactory.getBean(aspectName), aspectJAdviceMethod);
                break;
            case AspectJAnnotationType.AROUND:
                advice = new AspectJAroundAdvice(beanFactory.getBean(aspectName), aspectJAdviceMethod);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported advice type on method: " + this.aspectJAdviceMethod.getName());
        }
        advice.setAspectName(this.aspectName);
        return advice;
    }

    public String getAspectName() {
        return aspectName;
    }

    public Advice getInstantiatedAdvice() {
        return instantiatedAdvice;
    }
}
