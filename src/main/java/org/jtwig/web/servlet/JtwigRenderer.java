package org.jtwig.web.servlet;

import org.jtwig.environment.*;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.resolver.ReferenceRelativeResourceResolver;
import org.jtwig.resource.resolver.path.RelativePathResolver;
import org.jtwig.web.functions.PathFunction;
import org.jtwig.web.resource.WebResourceLoader;
import org.jtwig.web.servlet.model.factory.*;

import java.util.Collections;

public class JtwigRenderer {
    public static JtwigRenderer defaultRenderer () {
        return new JtwigRenderer(new DefaultEnvironmentConfiguration());
    }

    private final Environment environment;
    private final ApplicationFactory applicationFactory;

    public JtwigRenderer(EnvironmentConfiguration configuration) {
        EnvironmentFactory environmentFactory = new EnvironmentFactory();
        this.environment = environmentFactory.create(new EnvironmentConfigurationBuilder(configuration)
                .resources()
                    .relativeResourceResolvers().add(new ReferenceRelativeResourceResolver(Collections.singleton(WebResourceLoader.TYPE), RelativePathResolver.instance())).and()
                    .resourceLoaders().add(new TypedResourceLoader(WebResourceLoader.TYPE, new WebResourceLoader(ServletContextSupplier.instance()))).and()
                .and()
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
        ResourceReference resourceReference = environment.getResourceEnvironment().getResourceReferenceExtractor().extract(location);
        if (ResourceReference.ANY_TYPE.equals(resourceReference.getType())) {
            resourceReference = new ResourceReference(WebResourceLoader.TYPE, resourceReference.getPath());
        }
        return new JtwigDispatcher(environment, resourceReference, applicationFactory);
    }

    public JtwigDispatcher inlineDispatcherFor (String template) {
        return new JtwigDispatcher(environment, new ResourceReference(ResourceReference.STRING, template), applicationFactory);
    }

    public Environment getEnvironment() {
        return environment;
    }
}
