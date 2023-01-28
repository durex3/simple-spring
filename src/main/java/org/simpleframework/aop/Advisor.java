package org.simpleframework.aop;

/**
 * <h1>增强器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-26 21:32:20
 */
public interface Advisor {

    /**
     * <h2>返回一个增强</h2>
     * <p>前置增强、后置增强等</p>
     *
     * @return {@link Advice}
     */
    Advice getAdvice();
}
