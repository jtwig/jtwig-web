package org.jtwig.web.servlet.model;

import java.util.Map;

public class HttpRequest {
    private final String path;
    private final Map<String, Object> parameter;
    private final Map<String, Object> query;
    private final Map<String, Object> session;
    private final Map<String, Object> cookies;

    public HttpRequest(String path, Map<String, Object> parameter, Map<String, Object> query, Map<String, Object> session, Map<String, Object> cookies) {
        this.path = path;
        this.parameter = parameter;
        this.query = query;
        this.session = session;
        this.cookies = cookies;
    }

    public String getPath() {
        return path;
    }
    public Map<String, Object> getParameter() {
        return parameter;
    }
    public Map<String, Object> getQuery() {
        return query;
    }
    public Map<String, Object> getSession() {
        return session;
    }
    public Map<String, Object> getCookies() {
        return cookies;
    }
}
