package org.jtwig.web.servlet;

import javax.servlet.http.HttpServletResponse;

public class ServletResponseHolder {
    private static ThreadLocal<HttpServletResponse> holder = new ThreadLocal<>();

    public static void set (HttpServletResponse request) {
        holder.set(request);
    }

    public static HttpServletResponse get () {
        return holder.get();
    }
}
