package org.simpleframework.beans.factory;

/**
 * <h1>实现此接口可以获取当前 bean 的名字</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-20 19:15:11
 */
public interface BeanNameAware extends Aware {

    /**
     * <h2>设置名字</h2>
     *
     * @param name spring 中 Bean 的名字
     */
    void setBeanName(String name);
}
