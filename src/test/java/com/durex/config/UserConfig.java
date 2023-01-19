package com.durex.config;

import com.durex.component.User;
import org.simpleframework.context.annotation.Bean;
import org.simpleframework.context.annotation.Configuration;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-19 18:59:08
 */
@Configuration
public class UserConfig {

    @Bean(value = "user", initMethod = "init", destroyMethod = "close")
    public User getUser() {
        return new User("durex3");
    }
}
