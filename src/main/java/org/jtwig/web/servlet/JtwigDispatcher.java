package org.jtwig.web.servlet;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.configuration.Configuration;
import org.jtwig.web.resource.WebResource;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class JtwigDispatcher {
    private final JtwigModel model = new JtwigModel();
    private final Configuration configuration;
    private final String location;

    public JtwigDispatcher(Configuration configuration, String location) {
        this.configuration = configuration;
        this.location = location;
    }

    public JtwigDispatcher with (String name, Object value) {
        this.model.with(name, value);
        return this;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fillModelWithRequest(request);
        ServletRequestHolder.set(request);
        ServletResponseHolder.set(response);
        new JtwigTemplate(new WebResource(request.getServletContext()
                .getResource(location)), configuration)
                .render(model, response.getOutputStream());
    }

    private void fillModelWithRequest(ServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            model.with(element, request.getAttribute(element));
        }
    }
}
