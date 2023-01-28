package org.simpleframework.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.simpleframework.aop.MethodBeforeAdvice;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <h1>前置增强实现类</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 13:55:29
 */
public class AspectJMethodBeforeAdvice extends AbstractAspectJAdvice implements MethodBeforeAdvice {

    public AspectJMethodBeforeAdvice(Object aspectJAdviceObject, Method aspectJAdviceMethod) {
        super(aspectJAdviceObject, aspectJAdviceMethod);
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        ReflectionUtils.makeAccessible(this.aspectJAdviceMethod);
        JoinPoint joinPoint = new JoinPointImpl(args, target);
        List<Object> invokeArgs = getInvokeArgs(joinPoint);
        this.aspectJAdviceMethod.invoke(this.aspectJAdviceObject, invokeArgs.toArray());
    }
}
