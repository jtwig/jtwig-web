package org.jtwig.web.servlet;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.resource.Resource;
import org.jtwig.web.servlet.model.factory.ApplicationFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class JtwigResourceDispatcher {
    private final JtwigModel model = new JtwigModel();
    private final Environment environment;
    private final Resource resource;
    private final ApplicationFactory applicationFactory;

    public JtwigResourceDispatcher(Environment environment, Resource resource, ApplicationFactory applicationFactory) {
        this.environment = environment;
        this.resource = resource;
        this.applicationFactory = applicationFactory;
    }

    public JtwigResourceDispatcher with (String name, Object value) {
        this.model.with(name, value);
        return this;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fillModelWithAttributes(request);
        ServletRequestHolder.set(request);
        ServletResponseHolder.set(response);
        model.with("app", applicationFactory.create(request));
        new JtwigTemplate(environment, resource)
                .render(model, response.getOutputStream());
    }

    private void fillModelWithAttributes(ServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            model.with(element, request.getAttribute(element));
        }
    }
}
