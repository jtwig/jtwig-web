package org.jtwig.web.servlet;

import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.environment.EnvironmentFactory;
import org.jtwig.resource.StringResource;
import org.jtwig.web.functions.WebFunctions;
import org.jtwig.web.parser.addon.RenderNodeAddonProvider;
import org.jtwig.web.resource.resolver.WebResourceResolver;

import java.util.Collections;

public class JtwigRenderer {
    private final Environment environment;

    public JtwigRenderer(EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        EnvironmentFactory environmentFactory = new EnvironmentFactory();
        this.environment = environmentFactory.create(environmentConfigurationBuilder
                .resources().withResourceResolver(new WebResourceResolver()).and()
                .functions().withBeans(Collections.<Object>singletonList(new WebFunctions())).and()
                .parser().withAddOnParser(new RenderNodeAddonProvider()).and()
                .build());
    }

    public JtwigDispatcher dispatcherFor (String location) {
        return new JtwigDispatcher(environment, location);
    }

    public JtwigResourceDispatcher inlineDispatcherFor (String template) { return new JtwigResourceDispatcher(environment, new StringResource(template)); }
}
