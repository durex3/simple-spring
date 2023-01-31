package org.simpleframework.aop.framework;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.simpleframework.aop.*;
import org.simpleframework.aop.aspectj.AspectJAroundAdvice;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-28 01:11:50
 */
public class DynamicAdvisedInterceptor implements MethodInterceptor {
    private final AdvisedSupport advised;

    public DynamicAdvisedInterceptor(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        List<Advisor> advisors = advised.getAdvisors();
        TargetSource targetSource = advised.getTargetSource();
        Object target = targetSource.getTarget();
        Iterator<Advisor> it = advisors.iterator();
        while (it.hasNext()) {
            Advisor advisor = it.next();
            if (advisor instanceof PointcutAdvisor) {
                MethodMatcher methodMatcher = ((PointcutAdvisor) advisor).getPointcut().getMethodMatcher();
                if (!methodMatcher.matches(method, target.getClass())) {
                    it.remove();
                }
            } else {
                it.remove();
            }
        }

        Object retVal;
        if (advisors.isEmpty()) {
            retVal = methodProxy.invokeSuper(proxy, args);
        } else {
            // 其他增强
            List<Advisor> otherAdvisors = advisors.stream().filter(advisor ->
                            !(advisor.getAdvice() instanceof AspectJAroundAdvice))
                    .collect(Collectors.toList());
            if (!otherAdvisors.isEmpty()) {
                try {
                    invokeBeforeAdvices(otherAdvisors, method, args, target);
                    retVal = methodProxy.invokeSuper(proxy, args);
                    invokeAfterReturningAdvices(otherAdvisors, method, args, retVal, target);
                } catch (Exception e) {
                    invokeAfterThrowingAdvices(otherAdvisors, method, args, e, target);
                }
            }

            List<Advisor> aroundAdvisors = advisors.stream().filter(advisor ->
                            advisor.getAdvice() instanceof AspectJAroundAdvice)
                    .collect(Collectors.toList());
            // 环绕增强
            retVal = invokeAroundAdvices(aroundAdvisors, method, args, target);
        }

        return retVal;
    }

    private Object invokeAroundAdvices(List<Advisor> advisors, Method method, Object[] args, Object target) {
        Object result = null;
        for (Advisor advisor : advisors) {
            Advice advice = advisor.getAdvice();
            if (advice instanceof AspectJAroundAdvice) {
                try {
                    result = ((AspectJAroundAdvice) advice).invoke(method, args, target);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private void invokeAfterThrowingAdvices(List<Advisor> advisors, Method method, Object[] args, Throwable throwable, Object target) {
        advisors.forEach(advisor -> {
            Advice advice = advisor.getAdvice();
            if (advice instanceof AfterThrowingAdvice) {
                try {
                    ((AfterThrowingAdvice) advice).afterThrowing(throwable, method, args, target);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void invokeAfterReturningAdvices(List<Advisor> advisors, Method method, Object[] args, Object retVal, Object target) {
        advisors.forEach(advisor -> {
            Advice advice = advisor.getAdvice();
            if (advice instanceof AfterReturningAdvice) {
                try {
                    ((AfterReturningAdvice) advice).afterReturning(retVal, method, args, target);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void invokeBeforeAdvices(List<Advisor> advisors, Method method, Object[] args, Object target) {
        advisors.forEach(advisor -> {
            Advice advice = advisor.getAdvice();
            if (advice instanceof MethodBeforeAdvice) {
                try {
                    ((MethodBeforeAdvice) advice).before(method, args, target);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
