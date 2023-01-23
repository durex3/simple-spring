package com.durex.factorybean;

import org.simpleframework.beans.factory.FactoryBean;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-23 20:10:13
 */
@Component(value = "dragon")
public class DragonFactory implements FactoryBean<Dragon> {

    @Override
    public Dragon getObject() throws Exception {
        Dragon dragon = new Dragon();
        dragon.setName("durex3");
        return dragon;
    }

    @Override
    public Class<?> getObjectType() {
        return Dragon.class;
    }
}
