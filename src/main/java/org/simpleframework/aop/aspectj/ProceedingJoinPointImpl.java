package org.simpleframework.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;
import org.simpleframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-31 17:18:26
 */
public class ProceedingJoinPointImpl implements ProceedingJoinPoint {

    private final Object[] args;
    private final Object target;
    private final Method method;

    public ProceedingJoinPointImpl(Method method, Object[] args, Object target) {
        this.args = args;
        this.target = target;
        this.method = method;
    }

    @Override
    public void set$AroundClosure(AroundClosure arc) {

    }

    @Override
    public Object proceed() throws Throwable {
        if (args != null && args.length > 0) {
            return this.proceed(args);
        } else {
            ReflectionUtils.makeAccessible(method);
            return ReflectionUtils.invokeMethod(method, target);
        }
    }

    @Override
    public Object proceed(Object[] args) throws Throwable {
        ReflectionUtils.makeAccessible(method);
        return ReflectionUtils.invokeMethod(method, target, args);
    }

    @Override
    public String toShortString() {
        return null;
    }

    @Override
    public String toLongString() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }

    @Override
    public Object getTarget() {
        return this.target;
    }

    @Override
    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public Signature getSignature() {
        return null;
    }

    @Override
    public SourceLocation getSourceLocation() {
        return null;
    }

    @Override
    public String getKind() {
        return null;
    }

    @Override
    public StaticPart getStaticPart() {
        return null;
    }
}
