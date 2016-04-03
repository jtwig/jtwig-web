package org.jtwig.web.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.resource.Resource;
import org.jtwig.web.resource.WebResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebResourceResolverTest {
    private final ServletContext servletContext = mock(ServletContext.class);
    private WebResourceResolver underTest = new WebResourceResolver(){
        @Override
        protected ServletContext getServletContext() {
            return servletContext;
        }
    };

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void resolveOtherResourceType() throws Exception {
        Environment environment = mock(Environment.class);
        Resource resource = mock(Resource.class);

        Optional<Resource> result = underTest.resolve(environment, resource, "web:path");

        assertEquals(false, result.isPresent());
    }

    @Test
    public void resolve() throws Exception {
        Environment environment = mock(Environment.class);
        WebResource resource = mock(WebResource.class);

        when(resource.getFile()).thenReturn(new File("a"));

        Optional<Resource> result = underTest.resolve(environment, resource, "web:path");

        assertEquals(false, result.isPresent());
    }

    @Test
    public void resolveMalformedUrl() throws Exception {
        Environment environment = mock(Environment.class);
        WebResource resource = mock(WebResource.class);

        when(resource.getFile()).thenReturn(new File("a"));
        when(servletContext.getResource(anyString())).thenThrow(MalformedURLException.class);

        expectedException.expectMessage(containsString("Unable to resolve URL from file 'path'"));

        underTest.resolve(environment, resource, "web:path");
    }
}