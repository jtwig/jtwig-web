package org.jtwig.web.servlet;

import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.environment.EnvironmentFactory;
import org.jtwig.resource.StringResource;
import org.jtwig.web.functions.WebFunctions;
import org.jtwig.web.resource.resolver.WebResourceResolver;

import java.util.Collections;

public class JtwigRenderer {
    private final Environment environment;

    public JtwigRenderer(EnvironmentConfiguration configuration) {
        EnvironmentFactory environmentFactory = new EnvironmentFactory();
        this.environment = environmentFactory.create(new EnvironmentConfigurationBuilder(configuration)
                .resources().withResourceResolver(new WebResourceResolver()).and()
                .functions().withBeans(Collections.<Object>singletonList(new WebFunctions())).and()
                .build());
    }

    public JtwigDispatcher dispatcherFor (String location) {
        return new JtwigDispatcher(environment, location);
    }

    public JtwigResourceDispatcher inlineDispatcherFor (String template) { return new JtwigResourceDispatcher(environment, new StringResource(template)); }
}
