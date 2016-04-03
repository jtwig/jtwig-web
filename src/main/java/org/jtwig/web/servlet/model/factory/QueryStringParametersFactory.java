package org.jtwig.web.servlet.model.factory;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.*;

public class QueryStringParametersFactory {
    public Map<String, Object> create (HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        String queryString = request.getQueryString();
        if (queryString != null) {
            List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(queryString, Charset.defaultCharset());
            for (NameValuePair nameValuePair : nameValuePairs) {
                String key = nameValuePair.getName();
                if (result.containsKey(key)) {
                    Object value = result.get(key);
                    if (value instanceof Collection) {
                        ((Collection) value).add(nameValuePair.getValue());
                    } else {
                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(result.get(key));
                        objects.add(nameValuePair.getValue());
                        result.put(key, objects);
                    }
                } else {
                    result.put(key, nameValuePair.getValue());
                }
            }
        }
        return result;
    }
}
