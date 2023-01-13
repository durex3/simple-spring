package org.simpleframework.core.io;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-13 19:22:10
 */
class ResourceLoaderTest {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    void testLoadResource() throws IOException {
        Resource classPathResource = resourceLoader.getResource("classpath:application.properties");
        List<String> result = IOUtils.readLines(classPathResource.getInputStream(), StandardCharsets.UTF_8);
        Assertions.assertEquals("server.port=8080", result.get(0));

        Resource urlResource = resourceLoader.getResource("https://www.baidu.com");
        Assertions.assertNotNull(urlResource);

        Resource fileResource = resourceLoader.getResource("src/test/resources/application.properties");
        result = IOUtils.readLines(fileResource.getInputStream(), StandardCharsets.UTF_8);
        Assertions.assertEquals("server.port=8080", result.get(0));
    }
}
