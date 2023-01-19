package org.simpleframework.beans.factory;

import org.simpleframework.beans.BeansException;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 20:42:27
 */
public class BeanCreationException extends BeansException {

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(String message) {
        super(message);
    }
}
