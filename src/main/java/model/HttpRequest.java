package model;

import session.Cookie;
import type.RequestMethod;

import java.util.Collection;
import java.util.Map;

public class HttpRequest {
    private RequestMethod method;
    private String pathInfo;
    private String queryString;
    private String version;

    private Collection<Cookie> cookies;

    private Map<String, String> requestHeaders;
    private Map<String, String> requestBody;


    public Collection<Cookie> getCookies() {
        return this.cookies;
    }

    public void setCookies(Collection<Cookie> cookies) {
        this.cookies = cookies;
    }

    public void requestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getMethod() {
        return method.toString();
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public String getVersion() {
        return version;
    }

    public int getContentLength() {
        String contentLength = requestHeaders.get("Content-Length");
        return contentLength != null ? Integer.parseInt(contentLength) : 0;
    }

    public String getHeaderElement(String key) {
        return requestHeaders.get(key);
    }

    public void setMethod(String method) {
        this.method = RequestMethod.getMethod(method);
    }

    public String getQueryString() {
        return queryString;
    }

    public Map<String, String> getRequestBody() { return requestBody; };
    public void setVersion(String version) {
        this.version = version;
    }

    public void setRequestBody(Map<String, String> requestBody) {
        this.requestBody = requestBody;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

}
