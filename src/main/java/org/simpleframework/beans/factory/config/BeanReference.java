package org.simpleframework.beans.factory.config;

import org.simpleframework.beans.BeanMetadataElement;

/**
 * <h1> bean 对象的引用</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 19:42:11
 */
public interface BeanReference extends BeanMetadataElement {

    /**
     * <h2>返回此引用指向的目标 Bean 名称</h2>
     *
     * @return bean 名称
     */
    String getBeanName();
}
