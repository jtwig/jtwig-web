package org.jtwig.web.servlet;

import com.google.common.base.Optional;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.resource.Resource;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

public class JtwigDispatcher {
    private final JtwigModel model = new JtwigModel();
    private final Environment environment;
    private final String location;

    public JtwigDispatcher(Environment environment, String location) {
        this.environment = environment;
        this.location = location;
    }

    public JtwigDispatcher with (String name, Object value) {
        this.model.with(name, value);
        return this;
    }

    public JtwigDispatcher with (Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            this.model.with(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fillModelWithRequest(request);

        ServletRequestHolder.set(request);
        ServletResponseHolder.set(response);

        Optional<Resource> optional = environment.getResourceEnvironment().getResourceResolver().resolve(environment, null, location);
        if (optional.isPresent()) {
            new JtwigTemplate(environment, optional.get()).render(model, response.getOutputStream());
        } else {
            throw new IllegalArgumentException(String.format("Unable to load resource '%s'", location));
        }
    }

    private void fillModelWithRequest(ServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            model.with(element, request.getAttribute(element));
        }
    }
}
