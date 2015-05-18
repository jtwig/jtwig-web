package org.jtwig.web.resource;

import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class WebResource implements Resource {
    private final URL resource;

    public WebResource(URL resource) {
        this.resource = resource;
    }

    @Override
    public InputStream content() {
        try {
            return resource.openStream();
        } catch (IOException e) {
            throw new ResourceException(String.format("Cannot read resource %s", resource), e);
        }
    }

    public File getFile() {
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new ResourceException(String.format("Unable to get file from URL %s", resource), e);
        }
    }
}
