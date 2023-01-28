package org.simpleframework.aop;

/**
 * <h1>核心切入点</h1>
 * <p>定义描述匹配某些类的方法</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-26 20:33:38
 */
public interface Pointcut {

    /**
     * <h2>返回此切入点的类筛选器</h2>
     *
     * @return {@link ClassFilter}
     */
    ClassFilter getClassFilter();

    /**
     * <h2>返回此切入点的方法匹配器</h2>
     *
     * @return {@link MethodMatcher}
     */
    MethodMatcher getMethodMatcher();
}
