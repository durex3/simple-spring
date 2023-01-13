package org.simpleframework.beans.factory.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.config.RuntimeBeanReference;
import org.simpleframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.simpleframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.simpleframework.dao.UserDao;
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

        // 2.注入 bean
        BeanDefinition beanDefinition = new RootBeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService", "durex3");
        Assertions.assertNotNull(userService);
    }

    @Test
    void testGetBeanByPropertyValues() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new RootBeanDefinition(UserDao.class));

        // 3.UserService 注入 bean
        BeanDefinition beanDefinition = new RootBeanDefinition(UserService.class);
        beanDefinition.getPropertyValues().addPropertyValue("name", "durex3");
        beanDefinition.getPropertyValues().addPropertyValue("userDao", new RuntimeBeanReference("userDao"));
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 4.获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        Assertions.assertNotNull(userService);
        Assertions.assertEquals("durex3", userService.getName());
        Assertions.assertEquals(beanFactory.getBean("userDao"), userService.getUserDao());
    }

    @Test
    void testLoadBeanDefinitionByAnnotation() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.初始化 Scanner
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        int count = scanner.scan("org.simpleframework");
        Assertions.assertEquals(2, count);

        // 3.获取 bean
        UserService userService = (UserService) beanFactory.getBean("user");
        Assertions.assertNotNull(userService);
    }

    @Test
    void testLoadBeanDefinitionByXml() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.初始化 XmlBeanDefinitionReader
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3.获取 bean
        UserDao userDao = (UserDao) beanFactory.getBean("userDao");
        Assertions.assertNotNull(userDao);
        UserService userService = (UserService) beanFactory.getBean("userService");
        Assertions.assertNotNull(userService);
    }
}
