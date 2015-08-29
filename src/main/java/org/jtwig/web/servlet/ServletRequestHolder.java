package org.jtwig.web.servlet;

import javax.servlet.http.HttpServletRequest;

public class ServletRequestHolder {
    private static ThreadLocal<HttpServletRequest> holder = new ThreadLocal<HttpServletRequest>();

    public static void set (HttpServletRequest request) {
        holder.set(request);
    }

    public static HttpServletRequest get () {
        return holder.get();
    }
}
