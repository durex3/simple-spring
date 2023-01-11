package org.simpleframework.core.io.support;

import org.simpleframework.core.io.ClassPathResource;
import org.simpleframework.core.io.DefaultResourceLoader;
import org.simpleframework.core.io.Resource;
import org.simpleframework.core.io.ResourceLoader;
import org.simpleframework.util.ClassUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 19:55:48
 */
public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {

    private final ResourceLoader resourceLoader;

    public PathMatchingResourcePatternResolver() {
        this.resourceLoader = new DefaultResourceLoader();
    }

    public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader) {
        if (resourceLoader == null) {
            throw new IllegalArgumentException("ResourceLoader must not be null");
        }
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Resource getResource(String location) {
        return getResourceLoader().getResource(location);
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return findPathMatchingResources(locationPattern);
    }

    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    protected static Resource[] findPathMatchingResources(String locationPattern) throws IOException {
        locationPattern = locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()).replace(DEFAULT_RESOURCE_PATTERN, "");
        URL url = ClassUtils.getDefaultClassLoader().getResource(locationPattern);
        if (url == null) {
            throw new IllegalArgumentException("location pattern not found.");
        }
        String path = url.getPath();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        Path rootPath = Paths.get(path);
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + DEFAULT_RESOURCE_PATTERN);
        try (Stream<Path> fileAndDirs = Files.walk(rootPath)) {
            String finalLocationPattern = locationPattern;
            return fileAndDirs.filter(matcher::matches)
                    .map(p -> {
                        // D:/programming/project/Java/simple-spring/target/test-classes/org/simpleframework/service/UserDao.class
                        String absoluteFilePath = p.toString().replace("\\", "/");
                        // org/simpleframework/service/UserDao.class
                        String classPath = absoluteFilePath.substring(absoluteFilePath.lastIndexOf(finalLocationPattern));
                        return new ClassPathResource(classPath);
                    }).toArray(Resource[]::new);
        }
    }

}
