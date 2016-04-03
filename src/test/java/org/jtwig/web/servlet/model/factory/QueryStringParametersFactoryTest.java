package org.jtwig.web.servlet.model.factory;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QueryStringParametersFactoryTest {
    private QueryStringParametersFactory underTest = new QueryStringParametersFactory();

    @Test
    public void createList() throws Exception {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);

        when(servletRequest.getQueryString()).thenReturn("test=1&test=2");

        Map<String, Object> result = underTest.create(servletRequest);

        assertEquals(asList("1", "2"), result.get("test"));
    }
    @Test
    public void createList3() throws Exception {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);

        when(servletRequest.getQueryString()).thenReturn("test=1&test=2&test=3");

        Map<String, Object> result = underTest.create(servletRequest);

        assertEquals(asList("1", "2", "3"), result.get("test"));
    }
}