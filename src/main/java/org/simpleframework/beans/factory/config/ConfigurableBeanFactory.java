package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.factory.BeanFactory;
import org.simpleframework.util.StringValueResolver;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 13:30:09
 */
public interface ConfigurableBeanFactory extends SingletonBeanRegistry, BeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * <h2>销毁 bean 实例</h2>
     */
    void destroySingletons();

    ClassLoader getBeanClassLoader();

    /**
     * <h1>添加解析字符串值策略的接口</h1>
     *
     * @param valueResolver 字符串解析器
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * <h2>判断是否有字符串解析器</h2>
     *
     * @return true false
     */
    boolean hasEmbeddedValueResolver();

    /**
     * <h1>解析给定的模板值，例如 ${...}</h1>
     *
     * @param value 模板值
     * @return 解析后的实际值
     */
    String resolveEmbeddedValue(String value);
}
