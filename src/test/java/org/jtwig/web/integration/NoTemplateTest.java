package org.jtwig.web.integration;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.web.servlet.JtwigRenderer;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NoTemplateTest extends AbstractIntegrationTest {
    @Test
    public void noTemplateTest() throws Exception {
        Response response = Request.Get(serverUrl())
                .execute();

        assertThat(response.returnResponse().getStatusLine().getStatusCode(), is(500));
    }

    @Override
    protected void setUpContext(ServletContextHandler context) {
        context.addServlet(new ServletHolder(new HelloServlet()), "/*");
    }


    public static class HelloServlet extends HttpServlet {
        private final JtwigRenderer renderer = new JtwigRenderer(EnvironmentConfigurationBuilder.configuration().build());

        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setAttribute("one", "two");

            renderer.dispatcherFor("/WEB-INF/templates/unknown.twig")
                    .with("name", "Jtwig")
                    .render(request, response);
        }
    }
}
