package org.simpleframework.beans.factory;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 21:09:47
 */
public class BeanDefinitionStoreException extends RuntimeException {

    public BeanDefinitionStoreException(String message) {
        super(message);
    }

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
