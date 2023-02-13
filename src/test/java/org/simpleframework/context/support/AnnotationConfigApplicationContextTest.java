package org.simpleframework.context.support;

import com.durex.aop.LoginService;
import com.durex.aop.around.RegisterService;
import com.durex.autowired.Camel;
import com.durex.autowired.Pig;
import com.durex.autowired.Sheep;
import com.durex.autowired.Watermelon;
import com.durex.aware.Person;
import com.durex.component.User;
import com.durex.config.MyBeanFactoryPostProcessor;
import com.durex.config.MyBeanPostProcessor;
import com.durex.config.MyInstantiationAwareBeanPostProcessor;
import com.durex.dao.UserDao;
import com.durex.factorybean.Dragon;
import com.durex.factorybean.DragonFactory;
import com.durex.factorybean.Panda;
import com.durex.factorybean.PandaFactory;
import com.durex.scope.Cat;
import com.durex.scope.Dog;
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

    @Test
    void testScope() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.scope");
        // 2.获取 bean
        Cat cat1 = (Cat) applicationContext.getBean("cat");
        Cat cat2 = (Cat) applicationContext.getBean("cat");
        Assertions.assertEquals(cat1, cat2);

        Dog dog1 = (Dog) applicationContext.getBean("dog");
        Dog dog2 = (Dog) applicationContext.getBean("dog");
        Assertions.assertNotEquals(dog1, dog2);
    }

    @Test
    void testFactoryBean() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.factorybean");
        // 2.获取 bean
        Dragon dragon1 = (Dragon) applicationContext.getBean("dragon");
        Dragon dragon2 = (Dragon) applicationContext.getBean("dragon");
        Assertions.assertEquals(dragon1, dragon2);
        // 3.获取 factory bean
        DragonFactory dragonFactory = (DragonFactory) applicationContext.getBean("&dragon");
        Assertions.assertNotNull(dragonFactory);
    }

    @Test
    void testFactoryBeanByPrototype() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.factorybean");
        // 2.获取 bean
        Panda panda1 = (Panda) applicationContext.getBean("panda");
        Panda panda2 = (Panda) applicationContext.getBean("panda");
        Assertions.assertNotEquals(panda1, panda2);
        // 3.获取 factory bean
        PandaFactory pandaFactory = (PandaFactory) applicationContext.getBean("&panda");
        Assertions.assertNotNull(pandaFactory);
    }

    @Test
    void testAop() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.aop");
        // 2.获取 bean
        LoginService loginService = (LoginService) applicationContext.getBean("loginService");
        String result = loginService.login("durex3", "123456");
        Assertions.assertEquals("登录成功", result);
    }

    @Test
    void testAopAround() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.aop");
        // 2.获取 bean
        RegisterService registerService = (RegisterService) applicationContext.getBean("registerService");
        String result = registerService.register("durex3", "123456");
        Assertions.assertEquals("注册成功", result);
    }

    @Test
    void testAutowired() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.autowired");
        // 2.获取 bean
        Pig pig = (Pig) applicationContext.getBean("pig");
        Camel camel = (Camel) applicationContext.getBean("c");
        Sheep sheep = (Sheep) applicationContext.getBean("s");
        Assertions.assertNotNull(pig);
        Assertions.assertEquals(pig.getCamel(), camel);
        Assertions.assertEquals(pig.getCamel().getSheep(), sheep);
    }

    @Test
    void testQualifier() {
        // 1.创建 applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.durex.autowired");
        // 2.获取 bean
        Pig pig = (Pig) applicationContext.getBean("pig");
        Watermelon watermelon = (Watermelon) applicationContext.getBean("watermelon");
        Assertions.assertNotNull(pig);
        Assertions.assertEquals(pig.getFruits(), watermelon);
    }
}
