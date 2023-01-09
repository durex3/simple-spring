package org.simpleframework.service;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 13:30:42
 */
public class UserService {

    private String name;

    public UserService() {
    }

    public UserService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
