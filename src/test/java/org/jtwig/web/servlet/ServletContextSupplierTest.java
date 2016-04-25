package org.jtwig.web.servlet;

import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServletContextSupplierTest {
    private ServletContextSupplier underTest = ServletContextSupplier.instance();

    @Test
    public void getServletNull() throws Exception {
        ServletRequestHolder.set(null);
        ServletContext result = underTest.get();
        assertNull(result);
    }

    @Test
    public void getServletNotNull() throws Exception {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        ServletContext servletContext = mock(ServletContext.class);

        ServletRequestHolder.set(httpServletRequest);
        when(httpServletRequest.getServletContext()).thenReturn(servletContext);

        ServletContext result = underTest.get();

        assertSame(servletContext, result);
    }
}