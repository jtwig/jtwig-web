package org.jtwig.web.servlet;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.resource.Resource;
import org.jtwig.web.servlet.model.factory.ApplicationFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Vector;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;

public class JtwigDispatcherTest {
    private final ApplicationFactory applicationFactory = mock(ApplicationFactory.class);
    private final Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
    private final String location = "location";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void renderThrowsException() throws Exception {
        JtwigDispatcher underTest = new JtwigDispatcher(environment, location, applicationFactory);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getAttributeNames()).thenReturn(new Vector<String>().elements());
        when(environment.getResourceEnvironment().getResourceResolver().resolve(environment, null, location)).thenReturn(Optional.<Resource>absent());

        expectedException.expectMessage(containsString(String.format("Unable to load resource '%s'", location)));

        underTest.render(request, mock(HttpServletResponse.class));
    }

    @Test
    public void renderSimple() throws Exception {
        JtwigDispatcher underTest = new JtwigDispatcher(environment, location, applicationFactory);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Resource resource = mock(Resource.class);

        when(request.getAttributeNames()).thenReturn(new Vector<String>().elements());
        when(environment.getResourceEnvironment().getResourceResolver().resolve(environment, null, location)).thenReturn(Optional.of(resource));

        underTest.render(request, response);

        verify(applicationFactory).create(request);
    }
}