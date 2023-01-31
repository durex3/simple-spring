package org.simpleframework.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.simpleframework.aop.Advice;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <h1>环绕切面增强</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-31 17:12:15
 */
public class AspectJAroundAdvice extends AbstractAspectJAdvice implements Advice {

    public AspectJAroundAdvice(Object aspectJAdviceObject, Method aspectJAdviceMethod) {
        super(aspectJAdviceObject, aspectJAdviceMethod);
    }

    public Object invoke(Method method, Object[] args, Object target) throws Throwable {
        ReflectionUtils.makeAccessible(this.aspectJAdviceMethod);
        JoinPoint joinPoint = new ProceedingJoinPointImpl(method, args, target);
        List<Object> invokeArgs = getInvokeArgs(joinPoint);
        return this.aspectJAdviceMethod.invoke(this.aspectJAdviceObject, invokeArgs.toArray());
    }
}
