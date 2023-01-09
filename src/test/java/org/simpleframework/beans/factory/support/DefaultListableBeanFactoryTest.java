package org.simpleframework.beans.factory.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.service.UserService;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2022-12-31 13:16:51
 */
class DefaultListableBeanFactoryTest {

    @Test
    void testGetBean() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注册 bean
        BeanDefinition beanDefinition = new RootBeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3.第一次获取 bean
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        // 4.第二次获取 bean
        UserService userService2 = (UserService) beanFactory.getBean("userService");
        Assertions.assertEquals(userService1, userService2);
    }

    @Test
    void testGetBeanByArgs() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 注入bean
        BeanDefinition beanDefinition = new RootBeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.获取bean
        UserService userService = (UserService) beanFactory.getBean("userService", "durex3");
        Assertions.assertNotNull(userService);
    }
}
