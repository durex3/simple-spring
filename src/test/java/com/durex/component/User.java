package com.durex.component;

import org.simpleframework.beans.factory.DisposableBean;
import org.simpleframework.beans.factory.InitializingBean;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-19 18:46:29
 */
public class User implements InitializingBean, DisposableBean {

    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public void init() {
        System.out.println("User init 执行了");
    }

    public void close() {
        System.out.println("User close 执行了");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("User destroy 执行了");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("User afterPropertiesSet 执行了");
    }

    public String getName() {
        return name;
    }
}
