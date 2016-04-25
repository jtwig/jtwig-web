package org.jtwig.web.resource;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.junit.Test;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebResourceLoaderTest {
    private final Supplier<ServletContext> servletContextSupplier = mock(Supplier.class);
    private final WebResourceLoader underTest = new WebResourceLoader(servletContextSupplier);

    @Test
    public void getCharset() throws Exception {
        Optional<Charset> result = underTest.getCharset("test");
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void load() throws Exception {
        InputStream inputStream = mock(InputStream.class);
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContextSupplier.get()).thenReturn(servletContext);
        when(servletContext.getResourceAsStream("path")).thenReturn(inputStream);

        InputStream result = underTest.load("path");

        assertSame(inputStream, result);
    }

    @Test
    public void existsTrue() throws Exception {
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContextSupplier.get()).thenReturn(servletContext);
        when(servletContext.getResource("path")).thenReturn(new URL("file:/"));

        boolean result = underTest.exists("path");

        assertSame(true, result);
    }

    @Test
    public void existsFalse() throws Exception {
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContextSupplier.get()).thenReturn(servletContext);
        when(servletContext.getResource("path")).thenReturn(null);

        boolean result = underTest.exists("path");

        assertSame(false, result);
    }

    @Test
    public void toUrl() throws Exception {
        URL url = new URL("file:/");
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContextSupplier.get()).thenReturn(servletContext);
        when(servletContext.getResource("path")).thenReturn(url);

        Optional<URL> result = underTest.toUrl("path");

        assertSame(url, result.get());
    }
}