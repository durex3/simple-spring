package com.durex.autowired;

import org.simpleframework.beans.factory.annotation.Autowired;
import org.simpleframework.beans.factory.annotation.Qualifier;
import org.simpleframework.beans.factory.annotation.Value;
import org.simpleframework.context.annotation.PropertySource;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-06 16:21:39
 */
@PropertySource(value = {"classpath:application.properties"})
@Component
public class Pig {

    @Qualifier(value = "c")
    @Autowired
    private Camel camel;

    @Qualifier(value = "watermelon")
    @Autowired
    private Fruits fruits;

    @Value(value = "${durex3.name}")
    private String name;

    public Camel getCamel() {
        return camel;
    }

    public Fruits getFruits() {
        return fruits;
    }

    public String getName() {
        return name;
    }
}
