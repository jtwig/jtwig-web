package org.jtwig.web.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceException;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.web.resource.WebResource;

import java.io.File;
import java.net.MalformedURLException;

public class WebResourceResolver implements ResourceResolver {
    @Override
    public Optional<Resource> resolve(Resource resource, String relativePath) {
        File relativeFile = new File(relativePath);
        if(!relativeFile.isAbsolute()) {
            if(resource instanceof WebResource) {
                File parentFile = ((WebResource)resource).getFile().getParentFile();
                File file = new File(parentFile, relativePath);
                return this.resolve(file);
            } else {
                return this.resolve(relativeFile);
            }
        } else {
            return this.resolve(relativeFile);
        }
    }

    private Optional<Resource> resolve(File relativeFile) {
        try {
            return relativeFile.exists() ? Optional.<Resource>of(new WebResource(relativeFile.toURI().toURL())): Optional.<Resource>absent();
        } catch (MalformedURLException e) {
            throw new ResourceException(String.format("Unable to resolve URL from file %s", relativeFile), e);
        }
    }
}
