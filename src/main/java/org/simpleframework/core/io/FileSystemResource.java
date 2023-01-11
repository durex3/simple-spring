package org.simpleframework.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-11 12:59:06
 */
public class FileSystemResource implements Resource {

    private final String path;
    private final File file;


    public FileSystemResource(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null");
        }
        this.path = file.getPath();
        this.file = new File(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            return Files.newInputStream(this.file.toPath());
        } catch (NoSuchFileException ex) {
            throw new FileNotFoundException(ex.getMessage());
        }
    }

    @Override
    public URL getURL() throws IOException {
        return this.file != null ? this.file.toURI().toURL() : null;
    }
}
