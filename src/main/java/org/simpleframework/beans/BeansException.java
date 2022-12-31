package org.simpleframework.beans;

/**
 * @Author: liugelong
 * @createTime: 2022-12-31 12:27:16
 * @version: 1.0
 */
public class BeansException extends RuntimeException {

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeansException(String message) {
        super(message);
    }
}
