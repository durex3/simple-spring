package org.simpleframework.beans.factory;

/**
 * <h1>实现此接口可以获取当前 bean 的类加载器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-20 19:21:30
 */
public interface BeanClassLoaderAware extends Aware {

    /**
     * <h2>设置类加载器</h2>
     *
     * @param classLoader 类加载器
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
