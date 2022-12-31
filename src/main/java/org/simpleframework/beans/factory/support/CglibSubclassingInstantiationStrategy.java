package org.simpleframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.simpleframework.beans.BeansException;
import org.simpleframework.cglib.Durex3NamingPolicy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <h1>BeanFactories 中使用的默认对象实例化策略</h1>
 * <p>如果容器需要覆盖方法以实现方法注入，则使用 CGLIB 动态生成子类</p>
 * <p>jdk 9 之后需要添加 jvm 参数才能进行反射</p>
 *
 * @Author: liugelong
 * @createTime: 2022-12-31 22:28:55
 * @version: 1.0
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(RootBeanDefinition rd, String beanName, Constructor<?> ctor, Object... args) throws BeansException {
        try {
            Class<?> subclass = createEnhancedSubclass(rd);
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

    private Class<?> createEnhancedSubclass(RootBeanDefinition rd) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(rd.getBeanClass());
        enhancer.setNamingPolicy(Durex3NamingPolicy.INSTANCE);
        enhancer.setCallbackType(NoOp.class);
        return enhancer.createClass();
    }
}
