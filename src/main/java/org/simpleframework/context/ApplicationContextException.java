package org.simpleframework.context;

import org.simpleframework.beans.BeansException;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 11:44:45
 */
public class ApplicationContextException extends BeansException {

    public ApplicationContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
