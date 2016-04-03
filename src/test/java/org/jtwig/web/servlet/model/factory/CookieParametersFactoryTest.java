package org.jtwig.web.servlet.model.factory;

import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CookieParametersFactoryTest {
    private CookieParametersFactory underTest = new CookieParametersFactory();

    @Test
    public void createList() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        Cookie cookie1 = mock(Cookie.class);
        Cookie cookie2 = mock(Cookie.class);
        when(request.getCookies()).thenReturn(new Cookie[]{cookie1, cookie2});
        when(cookie1.getName()).thenReturn("test");
        when(cookie2.getName()).thenReturn("test");
        when(cookie1.getValue()).thenReturn("1");
        when(cookie2.getValue()).thenReturn("2");

        Map<String, Object> result = underTest.create(request);

        assertEquals(asList("1", "2"), result.get("test"));
    }
    @Test
    public void createList3() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        Cookie cookie1 = mock(Cookie.class);
        Cookie cookie2 = mock(Cookie.class);
        Cookie cookie3 = mock(Cookie.class);
        when(request.getCookies()).thenReturn(new Cookie[]{cookie1, cookie2, cookie3});
        when(cookie1.getName()).thenReturn("test");
        when(cookie2.getName()).thenReturn("test");
        when(cookie3.getName()).thenReturn("test");
        when(cookie1.getValue()).thenReturn("1");
        when(cookie2.getValue()).thenReturn("2");
        when(cookie3.getValue()).thenReturn("3");

        Map<String, Object> result = underTest.create(request);

        assertEquals(asList("1", "2", "3"), result.get("test"));
    }
}