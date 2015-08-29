package org.jtwig.web.servlet.model.factory;

import org.jtwig.web.servlet.model.Application;

import javax.servlet.http.HttpServletRequest;

public class ApplicationFactory {
    private final HttpRequestFactory httpRequestFactory;

    public ApplicationFactory(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }

    public Application create (HttpServletRequest request) {
        return new Application(httpRequestFactory.create(request));
    }
}
