package org.jtwig.web.servlet;

import org.apache.http.client.fluent.Request;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
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

public class ApplicationSessionVariableTest extends AbstractIntegrationTest {
    @Test
    public void variableTest() throws Exception {
        Request.Post(serverUrl() + "/session")
                .execute().returnContent().asString();
        String content = Request.Post(serverUrl() + "/other")
                .execute().returnContent().asString();

        assertThat(content, is("Hello four!"));
    }

    @Override
    protected void setUpContext(ServletContextHandler context) {
        context.addServlet(new ServletHolder(new SessionStoreServlet()), "/session");
        context.addServlet(new ServletHolder(new HelloServlet()), "/*");
        context.setSessionHandler(new SessionHandler(new HashSessionManager()));
    }


    public static class HelloServlet extends HttpServlet {
        private final JtwigRenderer renderer = new JtwigRenderer(ConfigurationBuilder.configuration());

        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            renderer.inlineDispatcherFor("Hello {{ app.request.session.get('one') }}!")
                    .render(request, response);
        }
    }


    public static class SessionStoreServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.getSession(true).setAttribute("one", "four");
        }
    }
}
