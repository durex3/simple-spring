package org.simpleframework.aop;

/**
 * <h1>用来过滤要生成代理的类和给定的类是否匹配</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-26 20:34:46
 */
public interface ClassFilter {

    /**
     * <h2>检查给定的类型是否匹配</h2>
     *
     * @param clazz 类型
     * @return true false
     */
    boolean matches(Class<?> clazz);
}
