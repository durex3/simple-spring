package org.simpleframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <h1>默认的资源加载器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 20:25:39
 */
public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location must not be null");
        }
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
