package org.jtwig.web.servlet.model;

public class Application {
    private final HttpRequest request;

    public Application(HttpRequest request) {
        this.request = request;
    }

    public HttpRequest getRequest() {
        return request;
    }
}
