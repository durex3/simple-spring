package org.simpleframework.beans.factory;

/**
 * <h1>bean 销毁实现的接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-18 20:03:24
 */
public interface DisposableBean {

    void destroy() throws Exception;
}
