package org.simpleframework.beans;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 12:27:16
 */
public class BeansException extends RuntimeException {

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeansException(String message) {
        super(message);
    }
}
