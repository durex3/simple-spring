package org.simpleframework.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;

/**
 * <h1>连接点简单实现</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-28 20:47:11
 */
public class JoinPointImpl implements JoinPoint {

    private final Object[] args;
    private final Object target;

    public JoinPointImpl(Object[] args, Object target) {
        this.args = args;
        this.target = target;
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
        return target;
    }

    @Override
    public Object[] getArgs() {
        return args;
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
