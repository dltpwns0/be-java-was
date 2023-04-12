package servlet;

import configure.ResolverConfigure;
import model.HttpRequest;
import model.HttpResponse;
import type.HttpMethod;
import util.HttpRequestParser;
import webserver.HttpResponseResolver;

import java.io.BufferedReader;
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
