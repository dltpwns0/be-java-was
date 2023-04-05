package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHead {
    private final String method;
    private String url;
    private String requestParams;
    private final String version;
    private final Map<String, String> MIMEHeader;

    public HttpRequestHead(String head) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(head));
        String[] requestLine = bufferedReader.readLine().split(" ");
        this.method = requestLine[0];
        setUrl(requestLine[1]);
        this.version = requestLine[2];

        this.MIMEHeader = new HashMap<>();
        String line = null;
        while (!(line = bufferedReader.readLine()).equals("")) {
            // TODO 헤더 바디에서, 하나의 헤더 요소의 값들은 1개 이상일 수 있다. 수정 필요
            String[] headerElement = line.split(": ");
            MIMEHeader.put(headerElement[0].toLowerCase(), headerElement[1]);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url.split("\\?")[0];
    }

    public String getVersion() {
        return version;
    }

    public boolean hasRequestParam() {
        return requestParams == null;
    }

    public String getHeaderElement(String key) {
        String lowerCaseOfKey = key.toLowerCase();
        return MIMEHeader.get(lowerCaseOfKey);
    }

    public String getRequestParam() {
        return requestParams;
    }

    private void setUrl(String url) {
        if (url.contains("?")) {
            setUrlAndRequestParams(url);
            return;
        }
        this.url = url;
    }

    private void setUrlAndRequestParams(String url) {
        String[]  UrlAndRequestParams = url.split("\\?");
        this.url = UrlAndRequestParams[0];
        this.requestParams = UrlAndRequestParams[1];
    }
}
