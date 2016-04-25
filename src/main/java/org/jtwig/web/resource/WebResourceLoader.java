package org.jtwig.web.resource;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.resource.loader.ResourceLoader;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class WebResourceLoader implements ResourceLoader {
    public static final String TYPE = "web";

    private final Supplier<ServletContext> servletContextSupplier;

    public WebResourceLoader(Supplier<ServletContext> servletContextSupplier) {
        this.servletContextSupplier = servletContextSupplier;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.absent();
    }

    @Override
    public InputStream load(String path) {
        return servletContextSupplier.get().getResourceAsStream(path);
    }

    @Override
    public boolean exists(String path) {
        try {
            return servletContextSupplier.get().getResource(path) != null;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @Override
    public Optional<URL> toUrl(String path) {
        try {
            return Optional.of(servletContextSupplier.get().getResource(path));
        } catch (MalformedURLException e) {
            return Optional.absent();
        }
    }
}
