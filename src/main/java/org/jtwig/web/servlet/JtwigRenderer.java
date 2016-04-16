package org.jtwig.web.servlet;

import org.jtwig.environment.*;
import org.jtwig.resource.StringResource;
import org.jtwig.web.functions.PathFunction;
import org.jtwig.web.resource.resolver.WebResourceResolver;
import org.jtwig.web.servlet.model.factory.*;

public class JtwigRenderer {
    public static JtwigRenderer defaultRenderer () {
        return new JtwigRenderer(new DefaultEnvironmentConfiguration());
    }

    private final Environment environment;
    private final ApplicationFactory applicationFactory;

    public JtwigRenderer(EnvironmentConfiguration configuration) {
        EnvironmentFactory environmentFactory = new EnvironmentFactory();
        this.environment = environmentFactory.create(new EnvironmentConfigurationBuilder(configuration)
                .resources().resourceResolvers().add(new WebResourceResolver()).and().and()
                .functions().add(new PathFunction()).and()
                .build());

        this.applicationFactory = new ApplicationFactory(new HttpRequestFactory(
                new FormParametersFactory(),
                new QueryStringParametersFactory(),
                new SessionParametersFactory(),
                new CookieParametersFactory()
        ));
    }

    public JtwigDispatcher dispatcherFor (String location) {
        return new JtwigDispatcher(environment, location, applicationFactory);
    }

    public JtwigResourceDispatcher inlineDispatcherFor (String template) { return new JtwigResourceDispatcher(environment, new StringResource(template), applicationFactory); }
}
