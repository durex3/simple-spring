package com.durex.autowired;

import org.simpleframework.beans.factory.annotation.Autowired;
import org.simpleframework.beans.factory.annotation.Qualifier;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-06 16:21:39
 */
@Component
public class Pig {

    @Qualifier(value = "c")
    @Autowired
    private Camel camel;

    @Qualifier(value = "watermelon")
    @Autowired
    private Fruits fruits;

    public Camel getCamel() {
        return camel;
    }

    public Fruits getFruits() {
        return fruits;
    }
}
