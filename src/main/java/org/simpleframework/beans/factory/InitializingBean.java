package org.simpleframework.beans.factory;

/**
 * <h1>bean 实现执行自定义初始化，或者只是检查是否已设置所有必需属性</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-18 18:59:41
 */
public interface InitializingBean {

    /**
     * <h2>bean 在属性赋值后执行</h2>
     */
    void afterPropertiesSet() throws Exception;
}
