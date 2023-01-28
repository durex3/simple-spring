package org.simpleframework.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.simpleframework.aop.AfterThrowingAdvice;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>后置异常切面增强</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-28 19:56:48
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice implements AfterThrowingAdvice {

    public AspectJAfterThrowingAdvice(Object aspectJAdviceObject, Method aspectJAdviceMethod) {
        super(aspectJAdviceObject, aspectJAdviceMethod);
    }

    @Override
    public void afterThrowing(Throwable e, Method method, Object[] args, Object target) throws Throwable {
        ReflectionUtils.makeAccessible(this.aspectJAdviceMethod);
        JoinPoint joinPoint = new JoinPointImpl(args, target);
        List<Object> invokeArgs = getInvokeArgs(joinPoint, e);
        this.aspectJAdviceMethod.invoke(this.aspectJAdviceObject, invokeArgs.toArray());
    }
}
