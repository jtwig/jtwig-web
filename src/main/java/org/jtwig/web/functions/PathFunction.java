package org.jtwig.web.functions;

import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.web.servlet.ServletRequestHolder;

import javax.servlet.http.HttpServletRequest;

public class PathFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "path";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.maximumNumberOfArguments(1);

        if (request.getNumberOfArguments() == 0) {
            return getHttpServletRequest().getContextPath();
        }
        return getHttpServletRequest().getContextPath() + request.get(0);
    }

    protected HttpServletRequest getHttpServletRequest() {
        return ServletRequestHolder.get();
    }
}
