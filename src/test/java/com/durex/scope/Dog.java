package com.durex.scope;

import org.simpleframework.beans.factory.config.ConfigurableBeanFactory;
import org.simpleframework.context.annotation.Scope;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-21 19:48:27
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Dog {
}
