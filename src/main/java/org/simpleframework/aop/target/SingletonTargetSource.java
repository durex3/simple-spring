package org.simpleframework.aop.target;

import org.simpleframework.aop.TargetSource;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:35:29
 */
public class SingletonTargetSource implements TargetSource {

    private final Object target;

    public SingletonTargetSource(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("Target object must not be null");
        }
        this.target = target;
    }

    @Override
    public Class<?> getTargetClass() {
        return target.getClass();
    }

    @Override
    public Object getTarget() {
        return target;
    }
}
