package org.simpleframework.core.io;

import org.simpleframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * <h1>类路径资源</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 23:46:03
 */
public class ClassPathResource implements Resource {

    private final String path;

    private final ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public URL getURL() throws IOException {
        try {
            if (this.classLoader != null) {
                return this.classLoader.getResource(this.path);
            } else {
                return ClassLoader.getSystemResource(this.path);
            }
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(
                    this.path + " cannot be opened because it does not exist");
        }
        return is;
    }
}

