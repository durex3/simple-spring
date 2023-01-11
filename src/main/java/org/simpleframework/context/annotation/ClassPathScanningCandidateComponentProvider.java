package org.simpleframework.context.annotation;

import org.simpleframework.beans.BeansException;
import org.simpleframework.beans.factory.BeanDefinitionStoreException;
import org.simpleframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.core.io.Resource;
import org.simpleframework.core.io.ResourceLoader;
import org.simpleframework.core.io.support.PathMatchingResourcePatternResolver;
import org.simpleframework.core.io.support.ResourcePatternResolver;
import org.simpleframework.stereotype.Component;
import org.simpleframework.stereotype.Controller;
import org.simpleframework.stereotype.Repository;
import org.simpleframework.stereotype.Service;
import org.simpleframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;

/**
 * <h1>类路径下组件扫描器</h1>
 * <p>扫描类路径下的 bean</p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 19:54:21
 */
public class ClassPathScanningCandidateComponentProvider {

    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    static final List<String> COMPONENT_ANNOTATION_CLASSNAME = Arrays.asList(
            "org.simpleframework.stereotype.Component",
            "org.simpleframework.stereotype.Controller",
            "org.simpleframework.stereotype.Service",
            "org.simpleframework.stereotype.Repository"
    );

    private ResourcePatternResolver resourcePatternResolver;

    private final List<Class<? extends Annotation>> includeFilters = new ArrayList<>(4);

    public ClassPathScanningCandidateComponentProvider() {
        registerDefaultFilters();
    }

    public final ResourceLoader getResourceLoader() {
        return getResourcePatternResolver();
    }

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        return scanCandidateComponents(basePackage);
    }

    public Set<BeanDefinition> scanCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;

        try {
            Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
            for (Resource resource : resources) {
                URL url = resource.getURL();
                Class<?> beanClass = loadClass(url, basePackage);
                for (Class<? extends Annotation> annotation : includeFilters) {
                    if (beanClass.isAnnotationPresent(annotation)) {
                        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
                        candidates.add(abd);
                    }
                }
            }
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning");
        }
        return candidates;
    }

    protected void registerDefaultFilters() {
        includeFilters.addAll(Arrays.asList(Component.class, Controller.class, Service.class, Repository.class));
    }

    protected boolean isStereotypeWithNameValue(String annotationType) {
        return COMPONENT_ANNOTATION_CLASSNAME.contains(annotationType);
    }

    private Class<?> loadClass(URL url, String basePackage) {
        String packagePath = ClassUtils.convertClassNameToResourcePath(basePackage);
        // /D:/programming/project/Java/simple-spring/target/test-classes/org/simpleframework/service/impl/UserServiceImpl.class
        String absoluteFilePath = url.getPath();
        // org/simpleframework/service/impl/UserServiceImpl.class
        String className = absoluteFilePath.substring(absoluteFilePath.lastIndexOf(packagePath))
                .replace("/", ".");
        // org.simpleframework.service.impl.UserServiceImpl
        className = className.substring(0, className.lastIndexOf("."));
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("load class error: ", e);
        }
    }

    private ResourcePatternResolver getResourcePatternResolver() {
        if (this.resourcePatternResolver == null) {
            this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
        }
        return this.resourcePatternResolver;
    }
}
