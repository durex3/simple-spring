package org.simpleframework.beans.factory.support;

import net.bytebuddy.ByteBuddy;
import org.simpleframework.beans.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <h1>Byte Buddy 是一个 JVM 的运行时代码生成器</h1>
 * <p>通过 Java Agent，或者在构建期间修改字节码</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-01 01:38:08
 */
public class ByteBuddyInstantiationStrategy extends SimpleInstantiationStrategy {

    @Override
    public Object instantiate(RootBeanDefinition rd, String beanName, Constructor<?> ctor, Object... args) throws BeansException {
        try {
            Class<?> subclass = createSubclass(rd);
            if (ctor == null) {
                return subclass.getDeclaredConstructor().newInstance();
            } else {
                return subclass.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }
        } catch (InstantiationException e) {
            throw new BeansException(beanName + " Is it an abstract class?");
        } catch (IllegalAccessException e) {
            throw new BeansException(beanName + " Is the constructor accessible?");
        } catch (InvocationTargetException e) {
            throw new BeansException(beanName + " Constructor threw exception " + e.getTargetException());
        } catch (NoSuchMethodException e) {
            throw new BeansException(beanName + " No default constructor found");
        }
    }

    private Class<?> createSubclass(RootBeanDefinition rd) {
        return new ByteBuddy()
                // 指定父类
                .subclass(rd.getBeanClass())
                // 产生字节码
                .make()
                // classLoader 加载字节码到内存
                .load(rd.getBeanClass().getClassLoader())
                // 获得class对象
                .getLoaded();
    }
}
