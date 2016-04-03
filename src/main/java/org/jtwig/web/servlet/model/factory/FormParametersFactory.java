package org.jtwig.web.servlet.model.factory;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormParametersFactory {
    public Map<String, Object> create (HttpServletRequest request) {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<NameValuePair> queryParameters = URLEncodedUtils.parse(request.getRequestURI(), Charset.defaultCharset());
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (!isQueryParameter(queryParameters, entry.getKey())) {
                if (entry.getValue().length == 1) {
                    hashMap.put(entry.getKey(), entry.getValue()[0]);
                } else {
                    ArrayList<Object> objects = new ArrayList<>();
                    for (String value : entry.getValue()) {
                        objects.add(value);
                    }
                    hashMap.put(entry.getKey(), objects);
                }
            }
        }
        return hashMap;
    }

    private boolean isQueryParameter(List<NameValuePair> nameValuePairs, String key) {
        for (NameValuePair nameValuePair : nameValuePairs) {
            if (key.equals(nameValuePair.getName())) {
                return true;
            }
        }
        return false;
    }
}
