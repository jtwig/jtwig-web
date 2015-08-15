package org.jtwig.web.resource;

import org.jtwig.resource.Resource;
import org.jtwig.web.servlet.ServletRequestHolder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;

public class WebResource implements Resource {
    private final String location;

    public WebResource(String location) {
        this.location = location;
    }

    @Override
    public InputStream content() {
        return getServletContext().getResourceAsStream(location);
    }

    protected ServletContext getServletContext() {
        return ServletRequestHolder.get().getServletContext();
    }

    public File getFile() {
        return new File(location);
    }
}
