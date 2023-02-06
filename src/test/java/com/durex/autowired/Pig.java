package com.durex.autowired;

import org.simpleframework.beans.factory.annotation.Autowired;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-06 16:21:39
 */
@Component
public class Pig {

    @Autowired
    private Camel camel;

    public Camel getCamel() {
        return camel;
    }
}
