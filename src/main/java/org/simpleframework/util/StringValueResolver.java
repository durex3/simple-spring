package org.simpleframework.util;

/**
 * <h1>用于解析字符串值的简单策略接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-20 14:20:33
 */
@FunctionalInterface
public interface StringValueResolver {
    String resolveStringValue(String strVal);
}
