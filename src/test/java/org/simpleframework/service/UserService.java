package org.simpleframework.service;

/**
 * @Author: liugelong
 * @createTime: 2022-12-31 13:30:42
 * @version: 1.0
 */
public class UserService {

    private String name;

    public UserService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
