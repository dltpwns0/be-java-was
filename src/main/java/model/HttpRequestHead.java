package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHead {
    private String method;
    private String url;
    private String version;
    private Map<String, String> requestParams;
    private Map<String, String> headerBody;

    public HttpRequestHead(String head) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(head));
        String[] requestLine = bufferedReader.readLine().split(" ");
        this.headerBody = new HashMap<>();
        this.method = requestLine[0];
        this.url = requestLine[1];
        this.version = requestLine[2];
        parseUrl();

        String line = null;
        while (!(line = bufferedReader.readLine()).equals("")) {
            // TODO 헤더 바디에서, 하나의 헤더 요소의 값들은 1개 이상일 수 있다. 수정 필요
            String[] headerElement = line.split(": ");
            headerBody.put(headerElement[0].toLowerCase(), headerElement[1]);
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

    public String getRequestParam(String key) {
        // TODO : 요청 받은 파마미터가 String 타입이 아닐 수 있다. 수정 필요
        return requestParams.get(key);
    }

    public String getHeaderElement(String key) {
        String lowerCaseOfKey = key.toLowerCase();
        return headerBody.get(lowerCaseOfKey);
    }

    private void parseUrl() {
        if (url.contains("?")) {
            parseRequestParams();
        }
    }

    private void parseRequestParams() {
        this.requestParams = new HashMap<>();
        String requestParams = url.split("\\?")[1];
        String[] params = requestParams.split("&");
        for (String param : params) {
            String[] keyAndVal = param.split("=");
            this.requestParams.put(keyAndVal[0], keyAndVal[1]);
        }
    }
}
