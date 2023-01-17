package org.simpleframework.context.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.simpleframework.conig.MyBeanFactoryPostProcessor;
import org.simpleframework.conig.MyBeanPostProcessor;
import org.simpleframework.conig.MyInstantiationAwareBeanPostProcessor;
import org.simpleframework.dao.UserDao;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-17 20:37:54
 */
class AnnotationConfigApplicationContextTest {


    @Test
    void testAnnotationConfigApplicationContextByScan() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.simpleframework");
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

}
