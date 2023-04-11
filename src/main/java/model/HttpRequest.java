package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private String path;
    private String query;
    private final String version;
    private final Map<String, String> requestHeaders;
    private Map<String, String> requestBody;

    private final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    public HttpRequest(BufferedReader bufferedReader) throws IOException {

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

        // TODO : 클린 코드가 절실한 상황!
        if (!requestHeaders.containsKey("content-length")) return;
        this.requestBody = new LinkedHashMap<>();
        int contentLength = Integer.parseInt(requestHeaders.get("content-length"));
        if (contentLength > 0) {
            char[] body = new char[contentLength];
            bufferedReader.read(body, 0, contentLength);

            String bodyOfHeader = URLDecoder.decode(new String(body), StandardCharsets.UTF_8);
            String[] bodyElement = bodyOfHeader.split("&");
            for (String pair: bodyElement) {
                String[] keyAndValue = pair.split("=");
                requestBody.put(keyAndValue[0], keyAndValue[1]);
            }
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
    public boolean hasBody() { return requestBody != null;}

    public String getHeaderElement(String key) {
        String lowerCaseOfKey = key.toLowerCase();
        return requestHeaders.get(lowerCaseOfKey);
    }

    public String getQuery() {
        return query;
    }
    public Map<String, String> getRequestBody() { return requestBody; };

    private void setMethod(String method) {
        if (method.equalsIgnoreCase("get")) {
            this.method = HttpMethod.GET;
            return;
        }
        if (method.equalsIgnoreCase("post")) {
            this.method = HttpMethod.POST;
            return;
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
