package org.simpleframework.context.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.simpleframework.dao.UserDao;

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
}
