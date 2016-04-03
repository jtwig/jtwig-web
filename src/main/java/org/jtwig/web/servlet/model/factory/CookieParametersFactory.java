package org.jtwig.web.servlet.model.factory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CookieParametersFactory {
    public Map<String, Object> create (HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String key = cookie.getName();
                if (result.containsKey(key)) {
                    final Object value = result.get(key);
                    if (value instanceof Collection) {
                        ((Collection) value).add(cookie.getValue());
                    } else {
                        Collection list = new ArrayList();
                        list.add(value);
                        list.add(cookie.getValue());
                        result.put(key, list);
                    }
                } else {
                    result.put(key, cookie.getValue());
                }
            }
        }
        return result;
    }
}
