package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.BeanDefinitionStoreException;
import org.simpleframework.core.io.Resource;
import org.simpleframework.core.io.ResourceLoader;

/**
 * <h1>Bean 定义信息读取器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-11 18:33:48
 */
public interface BeanDefinitionReader {

    /**
     * <h2>获取 bean 定义信息的注册中心</h2>
     *
     * @return {@link BeanDefinitionRegistry}
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * <h2>获取资源加载器</h2>
     *
     * @return {@link ResourceLoader}
     */
    ResourceLoader getResourceLoader();

    /**
     * <h2>按资源加载 bean 的定义信息</h2>
     *
     * @param resource 资源
     * @return 加载的 Bean 定义数量
     * @see org.simpleframework.core.io.ClassPathResource
     * @see org.simpleframework.core.io.UrlResource
     * @see org.simpleframework.core.io.FileSystemResource
     */
    int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException;

    /**
     * <h2>按多个资源加载 bean 的定义信息</h2>
     *
     * @param resources 多个资源
     * @return 加载的 Bean 定义数量
     * @see org.simpleframework.core.io.ClassPathResource
     * @see org.simpleframework.core.io.UrlResource
     * @see org.simpleframework.core.io.FileSystemResource
     */
    int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException;

    /**
     * <h2>按路径加载 bean 的定义信息</h2>
     *
     * @param location 路径
     * @return 加载的 Bean 定义数量
     * @see org.simpleframework.core.io.ClassPathResource
     * @see org.simpleframework.core.io.UrlResource
     * @see org.simpleframework.core.io.FileSystemResource
     */
    int loadBeanDefinitions(String location) throws BeanDefinitionStoreException;

    /**
     * <h2>按多个路径加载 bean 的定义信息</h2>
     *
     * @param locations 多个路径
     * @return 加载的 Bean 定义数量
     * @see org.simpleframework.core.io.ClassPathResource
     * @see org.simpleframework.core.io.UrlResource
     * @see org.simpleframework.core.io.FileSystemResource
     */
    int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException;
}
