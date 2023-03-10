package com.durex.service;

import com.durex.dao.UserDao;
import org.simpleframework.stereotype.Service;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 13:30:42
 */
@Service(value = "userService")
public class UserService {

    private UserDao userDao;
    private String name;

    public UserService() {
    }

    public UserService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setName(String name) {
        this.name = name;
    }
}
