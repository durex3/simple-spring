package org.simpleframework.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.simpleframework.aop.Advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 13:59:09
 */
public abstract class AbstractAspectJAdvice implements Advice {

    private String aspectName;
    protected Method aspectJAdviceMethod;
    protected Object aspectJAdviceObject;

    protected AbstractAspectJAdvice(Object aspectJAdviceObject, Method aspectJAdviceMethod) {
        this.aspectJAdviceObject = aspectJAdviceObject;
        this.aspectJAdviceMethod = aspectJAdviceMethod;
    }

    protected List<Object> getInvokeArgs(JoinPoint joinPoint, Object... args) {
        Class<?>[] parameterTypes = aspectJAdviceMethod.getParameterTypes();
        List<Object> invokeArgs = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            if (JoinPoint.class.isAssignableFrom(parameterType)) {
                invokeArgs.add(joinPoint);
            }
            for (Object arg : args) {
                if (parameterType.isAssignableFrom(arg.getClass())) {
                    invokeArgs.add(arg);
                }
            }
        }
        return invokeArgs;
    }

    public String getAspectName() {
        return aspectName;
    }

    public void setAspectName(String aspectName) {
        this.aspectName = aspectName;
    }
}
