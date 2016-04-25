package org.jtwig.web.servlet;

import com.google.common.base.Supplier;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletContextSupplier implements Supplier<ServletContext> {
    private static final ServletContextSupplier INSTANCE = new ServletContextSupplier();

    public static ServletContextSupplier instance () {
        return INSTANCE;
    }

    private ServletContextSupplier() {}

    @Override
    public ServletContext get() {
        HttpServletRequest httpServletRequest = ServletRequestHolder.get();
        if (httpServletRequest != null) {
            return httpServletRequest.getServletContext();
        } else {
            return null;
        }
    }
}
