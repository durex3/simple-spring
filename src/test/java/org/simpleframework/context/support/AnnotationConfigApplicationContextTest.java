package org.simpleframework.context.support;

import com.durex.aware.Person;
import com.durex.component.User;
import com.durex.config.MyBeanFactoryPostProcessor;
import com.durex.config.MyBeanPostProcessor;
import com.durex.config.MyInstantiationAwareBeanPostProcessor;
import com.durex.dao.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-17 20:37:54
 */
class AnnotationConfigApplicationContextTest {


    @Test
    void testAnnotationConfigApplicationContextByScan() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex");
        // 2.获取 bean
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        Assertions.assertNotNull(userDao);
    }

    @Test
    void testAnnotationConfigApplicationContextByRegister() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                UserDao.class,
                MyBeanFactoryPostProcessor.class,
                MyInstantiationAwareBeanPostProcessor.class,
                MyBeanPostProcessor.class
        );
        // 2.获取 bean
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        Assertions.assertNotNull(userDao);
    }

    @Test
    void testInitMethodAndDestroyMethod() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.config");
        User user = (User) applicationContext.getBean("user");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("durex3", user.getName());
        // 3.关闭容器
        applicationContext.registerShutdownHook();
    }

    @Test
    void testAware() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.aware");
        Person person = (Person) applicationContext.getBean("person");
        Assertions.assertNotNull(person);
    }
}
