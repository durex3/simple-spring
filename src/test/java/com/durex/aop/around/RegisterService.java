package com.durex.aop.around;

import org.simpleframework.stereotype.Service;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 21:58:54
 */
@Service
public class RegisterService {

    public String register(String username, String password) {
        System.out.println("注册中");
        return "注册成功";
    }
}
