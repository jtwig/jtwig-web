package org.jtwig.web.integration;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.junit.After;
import org.junit.Before;

import java.io.File;

public abstract class AbstractIntegrationTest {
    protected static int port;
    protected static Server server;

    @Before
    public void setUp() throws Exception {
        server = new Server(0);
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.setBaseResource(new FileResource(new File("src/main/webapp").getAbsoluteFile().toURI().toURL()));
        setUpContext(context);
        server.setHandler(context);
        server.start();

        port = ((ServerConnector) server.getConnectors()[0]).getLocalPort();

    }

    protected abstract void setUpContext(ServletContextHandler context);

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    public static String serverUrl() {
        return String.format("http://localhost:%d/", port);
    }
}

