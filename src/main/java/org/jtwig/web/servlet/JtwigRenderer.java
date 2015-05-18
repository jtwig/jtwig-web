package org.jtwig.web.servlet;

import org.jtwig.configuration.Configuration;
import org.jtwig.configuration.ConfigurationBuilder;
import org.jtwig.resource.Resource;
import org.jtwig.resource.StringResource;
import org.jtwig.web.functions.WebFunctions;
import org.jtwig.web.parser.addon.RenderNodeAddonProvider;
import org.jtwig.web.resource.resolver.WebResourceResolver;

public class JtwigRenderer {
    private final Configuration configuration;

    public JtwigRenderer(ConfigurationBuilder configuration) {
        this.configuration = configuration
                .withResourceResolver(new WebResourceResolver())
                .include(new WebFunctions())
                .withAddOnParser(new RenderNodeAddonProvider())
                .build();
    }

    public JtwigDispatcher dispatcherFor (String location) {
        return new JtwigDispatcher(configuration, location);
    }
    public JtwigResourceDispatcher dispatcherFor (Resource resource) { return new JtwigResourceDispatcher(configuration, resource); }
    public JtwigResourceDispatcher inlineDispatcherFor (String template) { return new JtwigResourceDispatcher(configuration, new StringResource(template)); }
}
