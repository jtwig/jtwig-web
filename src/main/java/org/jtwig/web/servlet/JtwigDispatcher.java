package org.jtwig.web.servlet;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.web.servlet.model.factory.ApplicationFactory;

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
    private final ResourceReference resourceReference;
    private final ApplicationFactory applicationFactory;

    public JtwigDispatcher(Environment environment, ResourceReference resourceReference, ApplicationFactory applicationFactory) {
        this.environment = environment;
        this.resourceReference = resourceReference;
        this.applicationFactory = applicationFactory;
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

        model.with("app", applicationFactory.create(request));

        ResourceMetadata resourceMetadata = environment.getResourceEnvironment().getResourceService().loadMetadata(resourceReference);
        if (!resourceMetadata.exists())
            throw new ResourceNotFoundException(String.format("Resource %s not found", resourceMetadata));

        JtwigTemplate jtwigTemplate = new JtwigTemplate(environment, resourceReference);
        jtwigTemplate.render(model, response.getOutputStream());
    }

    private void fillModelWithRequest(ServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            model.with(element, request.getAttribute(element));
        }
    }
}
