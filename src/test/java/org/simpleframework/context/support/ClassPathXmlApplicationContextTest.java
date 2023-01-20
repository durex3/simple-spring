package org.simpleframework.context.support;

import com.durex.aware.Person;
import com.durex.component.User;
import com.durex.dao.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 21:02:12
 */
class ClassPathXmlApplicationContextTest {

    @Test
    void testClassPathXmlApplicationContext() {
        // 1.创建 applicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        // 2.获取 bean
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        Assertions.assertNotNull(userDao);
    }

    @Test
    void testInitMethodAndDestroyMethod() {
        // 1.创建 applicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        // 2.获取 bean
        User user = (User) applicationContext.getBean("user");
        Assertions.assertNotNull(user);
        // 3.关闭容器
        applicationContext.registerShutdownHook();
    }

    @Test
    void testAware() {
        // 1.创建 applicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-aware.xml");
        Person person = (Person) applicationContext.getBean("person");
        Assertions.assertNotNull(person);
    }
}
