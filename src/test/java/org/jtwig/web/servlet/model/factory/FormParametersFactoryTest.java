package org.jtwig.web.servlet.model.factory;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FormParametersFactoryTest {
    private FormParametersFactory underTest = new FormParametersFactory();

    @Test
    public void createWithParameterRequest() throws Exception {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(httpServletRequest.getRequestURI()).thenReturn("test=hello");
        when(httpServletRequest.getParameterMap()).thenReturn(ImmutableMap.<String, String[]>builder()
                .put("test", new String[]{"hello"})
                .build());

        Map<String, Object> result = underTest.create(httpServletRequest);

        assertThat(result.size(), is(0));
    }

    @Test
    public void createWithParameterSingle() throws Exception {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(httpServletRequest.getRequestURI()).thenReturn("");
        when(httpServletRequest.getParameterMap()).thenReturn(ImmutableMap.<String, String[]>builder()
                .put("test", new String[]{"hello"})
                .build());

        Map<String, Object> result = underTest.create(httpServletRequest);

        assertThat(result.size(), is(1));
        assertEquals("hello", result.get("test"));
    }

    @Test
    public void createWithParameterMultiple() throws Exception {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(httpServletRequest.getRequestURI()).thenReturn("");
        when(httpServletRequest.getParameterMap()).thenReturn(ImmutableMap.<String, String[]>builder()
                .put("test", new String[]{"hello", "two"})
                .build());

        Map<String, Object> result = underTest.create(httpServletRequest);

        assertThat(result.size(), is(1));
        assertEquals(asList("hello", "two"), result.get("test"));
    }
}