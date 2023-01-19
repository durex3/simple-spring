package org.simpleframework.beans.factory;

import org.simpleframework.beans.BeansException;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-19 14:32:43
 */
public class NoSuchBeanDefinitionException extends BeansException {

    public NoSuchBeanDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }
}
