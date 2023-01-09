package org.simpleframework.beans;

/**
 * <h1>Bean 元数据元素实现的接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 19:43:04
 */
public interface BeanMetadataElement {

    /**
     * <h2>返回此元数据</h2>
     */
    default Object getSource() {
        return null;
    }
}
