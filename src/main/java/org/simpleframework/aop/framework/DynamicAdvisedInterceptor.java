package org.simpleframework.aop.framework;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.simpleframework.aop.*;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

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

        Object retVal = null;
        try {
            if (advisors.isEmpty()) {
                retVal = methodProxy.invokeSuper(proxy, args);
            } else {
                // 执行 before
                invokeBeforeAdvices(method, args, target);
                retVal = methodProxy.invokeSuper(proxy, args);
                invokeAfterReturningAdvices(method, args, retVal, target);
            }
        } catch (Exception e) {
            invokeAfterThrowingAdvices(method, args, e, target);
        }
        return retVal;
    }

    private void invokeAfterThrowingAdvices(Method method, Object[] args, Throwable throwable, Object target) {
        this.advised.getAdvisors().forEach(advisor -> {
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

    private void invokeAfterReturningAdvices(Method method, Object[] args, Object retVal, Object target) {
        this.advised.getAdvisors().forEach(advisor -> {
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


    private void invokeBeforeAdvices(Method method, Object[] args, Object target) {
        this.advised.getAdvisors().forEach(advisor -> {
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
