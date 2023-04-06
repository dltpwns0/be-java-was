package model;

import util.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private String path;
    private String query;
    private final String version;
    private final Map<String, String> requestHeaders;

    public HttpRequest(String requestHead) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(requestHead));
        String[] requestLine = bufferedReader.readLine().split(" ");
        setMethod(requestLine[0]);
        setPath(requestLine[1]);
        this.version = requestLine[2];

        this.requestHeaders = new HashMap<>();
        String line = null;
        while (!(line = bufferedReader.readLine()).equals("")) {
            // TODO 헤더 바디에서, 하나의 헤더 요소의 값들은 1개 이상일 수 있다. 수정 필요
            String[] headerElement = line.split(": ");
            requestHeaders.put(headerElement[0].toLowerCase(), headerElement[1]);
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.split("\\?")[0];
    }

    public String getVersion() {
        return version;
    }

    public boolean hasQuery() {
        return query != null;
    }

    public String getHeaderElement(String key) {
        String lowerCaseOfKey = key.toLowerCase();
        return requestHeaders.get(lowerCaseOfKey);
    }

    public String getQuery() {
        return query;
    }

    private void setMethod(String method) {
        if (method.equalsIgnoreCase("get")) {
            this.method = HttpMethod.GET;
        }
    }

    private void setPath(String path) {
        String decodedUrl = URLDecoder.decode(path, StandardCharsets.UTF_8);
        if (decodedUrl.contains("?")) {
            setPathAndQuery(decodedUrl);
            return;
        }
        this.path = decodedUrl;
    }

    private void setPathAndQuery(String url) {
        String[]  pathAndQuery = url.split("\\?");
        this.path = pathAndQuery[0];
        this.query = pathAndQuery[1];
    }
}
