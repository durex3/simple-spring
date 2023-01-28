package org.simpleframework.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.simpleframework.aop.AfterReturningAdvice;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <h1>后置返回切面增强</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-28 19:33:52
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice implements AfterReturningAdvice {

    public AspectJAfterReturningAdvice(Object aspectJAdviceObject, Method aspectJAdviceMethod) {
        super(aspectJAdviceObject, aspectJAdviceMethod);
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        ReflectionUtils.makeAccessible(this.aspectJAdviceMethod);
        JoinPoint joinPoint = new JoinPointImpl(args, target);
        List<Object> invokeArgs = getInvokeArgs(joinPoint, returnValue);
        this.aspectJAdviceMethod.invoke(this.aspectJAdviceObject, invokeArgs.toArray());
    }
}
