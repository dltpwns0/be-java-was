package servlet;

import type.HttpMethod;
import java.util.HashMap;
import java.util.Map;

public class HttpServletContainer {

    private final Map<HttpMethod , HttpServlet> servletMap;

    public HttpServletContainer() {
        this.servletMap = new HashMap<>();
        servletMap.put(HttpMethod.GET, new HttpGetServlet());
        servletMap.put(HttpMethod.POST, new HttpPostServlet());

    }

    public HttpServlet getServlet(String method)  {
        HttpMethod httpMethod = HttpMethod.getMethod(method);

        return servletMap.get(httpMethod);
    }

}
