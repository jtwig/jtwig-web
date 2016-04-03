package org.jtwig.web.servlet.model.factory;

import org.jtwig.web.servlet.model.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestFactory {
    private final FormParametersFactory formParametersFactory;
    private final QueryStringParametersFactory queryStringParametersFactory;
    private final SessionParametersFactory sessionParametersFactory;
    private final CookieParametersFactory cookieParametersFactory;

    public HttpRequestFactory(FormParametersFactory formParametersFactory,
                              QueryStringParametersFactory queryStringParametersFactory,
                              SessionParametersFactory sessionParametersFactory, CookieParametersFactory cookieParametersFactory) {
        this.formParametersFactory = formParametersFactory;
        this.queryStringParametersFactory = queryStringParametersFactory;
        this.sessionParametersFactory = sessionParametersFactory;
        this.cookieParametersFactory = cookieParametersFactory;
    }

    public HttpRequest create (HttpServletRequest request) {
        return new HttpRequest(
                formParametersFactory.create(request),
                queryStringParametersFactory.create(request),
                sessionParametersFactory.create(request),
                cookieParametersFactory.create(request)
        );
    }
}
