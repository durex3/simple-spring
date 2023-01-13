package org.simpleframework.beans.factory.support;

import org.simpleframework.beans.factory.BeanDefinitionStoreException;
import org.simpleframework.core.io.Resource;
import org.simpleframework.core.io.ResourceLoader;
import org.simpleframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * <h1>抽象的 Bean 定义信息读取器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-11 18:40:00
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        if (registry == null) {
            throw new IllegalArgumentException("BeanDefinitionRegistry must not be null");
        }
        this.registry = registry;

        if (this.registry instanceof ResourceLoader) {
            this.resourceLoader = (ResourceLoader) this.registry;
        } else {
            this.resourceLoader = new PathMatchingResourcePatternResolver();
        }
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    @Override
    public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
        if (resources == null) {
            throw new IllegalArgumentException("Resource array must not be null");
        }
        int count = 0;
        for (Resource resource : resources) {
            count += loadBeanDefinitions(resource);
        }
        return count;
    }

    @Override
    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        if (resourceLoader == null) {
            throw new BeanDefinitionStoreException(
                    "Cannot load bean definitions from location [" + location + "]: no ResourceLoader available");
        }

        Resource resource = resourceLoader.getResource(location);
        return loadBeanDefinitions(resource);
    }

    @Override
    public int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
        if (locations == null) {
            throw new IllegalArgumentException("Location array must not be null");
        }
        int count = 0;
        for (String location : locations) {
            count += loadBeanDefinitions(location);
        }
        return count;
    }

    public final BeanDefinitionRegistry getBeanFactory() {
        return this.registry;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
