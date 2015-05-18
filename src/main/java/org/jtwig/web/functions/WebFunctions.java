package org.jtwig.web.functions;

import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.web.servlet.ServletRequestHolder;

import javax.servlet.http.HttpServletRequest;

public class WebFunctions {
    @JtwigFunction("path")
    public String path () {
        return getHttpServletRequest().getContextPath();
    }

    @JtwigFunction("path")
    public String path (@Parameter("path") String path) {
        return getHttpServletRequest().getContextPath() + path;
    }

    protected HttpServletRequest getHttpServletRequest() {
        return ServletRequestHolder.get();
    }
}
