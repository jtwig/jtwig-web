package org.jtwig.web.servlet;

import org.apache.http.client.fluent.Request;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PathFunctionTest extends AbstractIntegrationTest {
    @Test
    public void pathTest() throws Exception {

        String content = Request.Get(String.format("%s/hello", serverUrl()))
                .execute().returnContent().asString();

        assertThat(content, is("/hello - /hello/one"));
    }

    @Override
    protected void setUpContext(ServletContextHandler context) {
        context.setContextPath("/hello");
        context.addServlet(new ServletHolder(new PathTestServlet()), "/*");
    }

    public static class PathTestServlet extends HttpServlet {
        private final JtwigRenderer renderer = new JtwigRenderer(EnvironmentConfigurationBuilder.configuration());

        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            renderer.inlineDispatcherFor("{{ path() }} - {{ path('/one') }}")
                    .render(request, response);
        }
    }
}
