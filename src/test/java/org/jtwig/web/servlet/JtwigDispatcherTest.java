package org.jtwig.web.servlet;

import org.jtwig.environment.Environment;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.web.resource.WebResourceLoader;
import org.jtwig.web.servlet.model.factory.ApplicationFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Vector;

import static org.mockito.Mockito.*;

public class JtwigDispatcherTest {
    private final ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    private final Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
    private final ResourceReference resourceReference = new ResourceReference(WebResourceLoader.TYPE, "location");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void renderSimple() throws Exception {
        JtwigDispatcher underTest = new JtwigDispatcher(environment, resourceReference, applicationFactory);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getAttributeNames()).thenReturn(new Vector<String>().elements());

        underTest.render(request, response);

        verify(applicationFactory).create(request);
    }
}