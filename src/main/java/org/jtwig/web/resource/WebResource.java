package org.jtwig.web.resource;

import org.jtwig.resource.Resource;
import org.jtwig.web.servlet.ServletRequestHolder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

public class WebResource implements Resource {
    private final String location;
    private final Charset charset;

    public WebResource(Charset charset, String location) {
        this.charset = charset;
        this.location = location;
    }

    protected ServletContext getServletContext() {
        return ServletRequestHolder.get().getServletContext();
    }

    public File getFile() {
        return new File(location);
    }

    @Override
    public InputStream getContent() {
        return getServletContext().getResourceAsStream(location);
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WebResource that = (WebResource) o;

        return that.location.equals(location);
    }
}
