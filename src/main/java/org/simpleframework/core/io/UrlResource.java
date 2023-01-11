package org.simpleframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * <h1> url 资源</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 21:22:32
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("URI must not be null");
        }
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        } catch (IOException ex) {
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }

    @Override
    public URL getURL() throws IOException {
        return url;
    }
}
