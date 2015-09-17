package org.jtwig.web.functions;

import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.web.servlet.ServletRequestHolder;

import javax.servlet.http.HttpServletRequest;

public class PathFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "path";
    }

    @Override
    public Object execute(JtwigFunctionRequest request) {
        request.maximumNumberOfArguments(1);

        if (request.getNumberOfArguments() == 0) {
            return getHttpServletRequest().getContextPath();
        }
        return getHttpServletRequest().getContextPath() + request.getArgument(0, String.class);
    }

    protected HttpServletRequest getHttpServletRequest() {
        return ServletRequestHolder.get();
    }
}
