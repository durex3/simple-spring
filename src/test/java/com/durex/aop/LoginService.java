package com.durex.aop;

import org.simpleframework.stereotype.Service;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 21:58:54
 */
@Service
public class LoginService {

    public String login(String username, String password) {
        System.out.println("登录中");
        return "登录成功";
    }
}
