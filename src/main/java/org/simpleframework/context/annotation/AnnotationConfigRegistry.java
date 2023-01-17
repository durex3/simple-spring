package org.simpleframework.context.annotation;

/**
 * <h1>用于注解配置应用程序上下文的通用接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-17 19:17:28
 */
public interface AnnotationConfigRegistry {

    /**
     * <h2>按类注册</h2>
     *
     * @param componentClasses 类型
     */
    void register(Class<?>... componentClasses);

    /**
     * <h2>包扫描</h2>
     *
     * @param basePackages 包路径
     */
    void scan(String... basePackages);
}
