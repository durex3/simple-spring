package org.simpleframework.beans.factory.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.service.UserService;

/**
 * @Author: liugelong
 * @createTime: 2022-12-31 13:16:51
 * @version: 1.0
 */
class DefaultListableBeanFactoryTest {

    @Test
    void getBean() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注册 bean
        BeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3.第一次获取 bean
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        // 4.第二次获取 bean
        UserService userService2 = (UserService) beanFactory.getBean("userService");
        Assertions.assertEquals(userService1, userService2);
    }
}
