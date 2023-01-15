package org.simpleframework.core.io.support;

import org.simpleframework.core.io.DefaultResourceLoader;
import org.simpleframework.core.io.FileSystemResource;
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

    protected Resource[] findPathMatchingResources(String locationPattern) throws IOException {
        // classpath*:org/simpleframework/**/*.class
        String location = locationPattern;
        if (locationPattern.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
            // classpath*:org/simpleframework/
            location = determineRootDir(locationPattern);
        }
        // **/*.class
        String subPattern = locationPattern.substring(location.length());
        // org/simpleframework/
        location = location.substring(CLASSPATH_ALL_URL_PREFIX.length());

        URL url = ClassUtils.getDefaultClassLoader().getResource(location);
        if (url == null) {
            throw new IllegalArgumentException("location pattern not found.");
        }
        String path = url.getPath();
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        Path rootPath = Paths.get(path);

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + subPattern);
        try (Stream<Path> fileAndDirs = Files.walk(rootPath)) {
            return fileAndDirs.filter(matcher::matches).map(p -> {
                // D:/programming/project/Java/simple-spring/target/test-classes/org/simpleframework/service/UserDao.class
                String absoluteFilePath = p.toString().replace("\\", "/");
                return new FileSystemResource(absoluteFilePath);
            }).toArray(Resource[]::new);
        }
    }

    protected String determineRootDir(String location) {
        // classpath*:org/simpleframework/**/*.class
        int prefixEnd = location.indexOf(':') + 1;
        int rootDirEnd = location.length();
        while (rootDirEnd > prefixEnd && location.substring(prefixEnd, rootDirEnd).contains("*")) {
            rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
        }
        if (rootDirEnd == 0) {
            rootDirEnd = prefixEnd;
        }
        return location.substring(0, rootDirEnd);
    }
}
