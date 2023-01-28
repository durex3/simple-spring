package org.simpleframework.aop;

import java.lang.reflect.Method;

/**
 * <h1>用来判断方法是否需要进行增强</h1>
 * <p>{@code ClassFilter} 过滤需要生成代理的类。而这个类里，不是所有的方法都需要增强的，所以要通过此接口匹配出要增强的方法来</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-26 20:35:05
 */
public interface MethodMatcher {

    /**
     * <h2>检查给定的 class 的 method 是否匹配</h2>
     *
     * @param method      方法
     * @param targetClass 目标类型
     * @return true false
     */
    boolean matches(Method method, Class<?> targetClass);

    /**
     * <h2>检查给定的 class 的 method 是否匹配</h2>
     *
     * @param method      方法
     * @param targetClass 目标类型
     * @param args        方法的参数
     * @return true false
     */
    boolean matches(Method method, Class<?> targetClass, Object... args);
}
