package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.MutablePropertyValues;

/**
 * <h1>bean 的定义信息</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-30 22:17:29
 */
public interface BeanDefinition {

    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    /**
     * <h2>设置 bean 的类名</h2>
     *
     * @param beanClassName 类名
     */
    void setBeanClassName(String beanClassName);

    /**
     * <h2>获取设置 bean 的类名</h2>
     *
     * @return 类名
     */
    String getBeanClassName();

    /**
     * <h2>设置 bean 的作用域</h2>
     *
     * @param scope 作用域
     */
    void setScope(String scope);

    /**
     * <h2>获取 bean 的作用域</h2>
     *
     * @return 作用域
     */
    String getScope();

    /**
     * <h2>设置 bean 是否是懒加载</h2>
     *
     * @param lazyInit ture false
     */
    void setLazyInit(boolean lazyInit);

    /**
     * <h2>判断 bean 是否是懒加载</h2>
     *
     * @return true false
     */
    boolean isLazyInit();

    /**
     * <h2>判断是否是单例</h2>
     *
     * @return true false
     */
    boolean isSingleton();

    /**
     * <h2>获取 PropertyValues</h2>
     *
     * @return {@link MutablePropertyValues}
     */
    MutablePropertyValues getPropertyValues();

    /**
     * <h2>判断是否有PropertyValues </h2>
     */
    default boolean hasPropertyValues() {
        return !getPropertyValues().isEmpty();
    }

    /**
     * <h2>设置初始化方法</h2>
     *
     * @param initMethodName 初始化方法名
     */
    void setInitMethodName(String initMethodName);

    /**
     * <h2>获取初始化方法</h2>
     *
     * @return 初始化方法名
     */
    String getInitMethodName();

    /**
     * <h2>设置销毁方法</h2>
     *
     * @param destroyMethodName 销毁方法名
     */
    void setDestroyMethodName(String destroyMethodName);

    /**
     * <h2>获取销毁方法</h2>
     *
     * @return 销毁方法名
     */
    String getDestroyMethodName();
}
