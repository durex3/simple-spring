package com.durex.factorybean;

import org.simpleframework.beans.factory.FactoryBean;
import org.simpleframework.stereotype.Component;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-23 20:10:13
 */
@Component(value = "panda")
public class PandaFactory implements FactoryBean<Panda> {

    @Override
    public Panda getObject() throws Exception {
        Panda panda = new Panda();
        panda.setName("durex3");
        return panda;
    }

    @Override
    public Class<?> getObjectType() {
        return Panda.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
