package org.simpleframework.aop;

/**
 * <h1>切入点增强器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-26 21:35:32
 */
public interface PointcutAdvisor extends Advisor {

    /**
     * <h2>返回切入点</h2>
     *
     * @return {@link Pointcut}
     */
    Pointcut getPointcut();
}
