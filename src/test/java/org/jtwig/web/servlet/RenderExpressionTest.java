package org.jtwig.web.servlet;

import org.apache.http.client.fluent.Request;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jtwig.configuration.ConfigurationBuilder;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RenderExpressionTest extends AbstractIntegrationTest {
    @Test
    public void renderTest() throws Exception {

        String content = Request.Get(String.format("%s/render", serverUrl()))
                .execute().returnContent().asString();

        assertThat(content, is("Hello World"));
    }

    @Override
    protected void setUpContext(ServletContextHandler context) {
        context.addServlet(new ServletHolder(new HelloServlet()), "/hello");
        context.addServlet(new ServletHolder(new RenderServlet()), "/render");
    }


    public static class HelloServlet extends HttpServlet {
        private final JtwigRenderer renderer = new JtwigRenderer(ConfigurationBuilder.configuration());

        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            renderer.inlineDispatcherFor("World")
                    .render(request, response);
        }
    }


    public static class RenderServlet extends HttpServlet {
        private final JtwigRenderer renderer = new JtwigRenderer(ConfigurationBuilder.configuration());

        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            renderer.inlineDispatcherFor("Hello {% render '/hello' %}")
                    .render(request, response);
        }
    }
}
