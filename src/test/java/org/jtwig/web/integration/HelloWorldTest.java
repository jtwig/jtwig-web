package org.jtwig.web.integration;

import org.apache.http.client.fluent.Request;
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

public class HelloWorldTest extends AbstractIntegrationTest {


    @Test
    public void helloWorldTest() throws Exception {
        String content = Request.Get(serverUrl())
                .execute().returnContent().asString();

        assertThat(content, is("Hello Jtwigtwo!"));
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

            renderer.dispatcherFor("web:/WEB-INF/templates/example.twig")
                    .with("name", "Jtwig")
                    .render(request, response);
        }
    }
}
