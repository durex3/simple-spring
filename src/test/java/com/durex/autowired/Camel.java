package com.durex.autowired;

import org.simpleframework.beans.factory.annotation.Autowired;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-06 16:21:48
 */
@Component
public class Camel {

    private Sheep s;

    public Sheep getSheep() {
        return s;
    }

    @Autowired
    public void setSheep(Sheep sheep) {
        this.s = sheep;
    }
}
