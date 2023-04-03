package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Head {
    private String method;
    private String url;
    private String version;
    private Map<String, String> headerBody;

    public Head(String head) throws IOException {
        this.headerBody = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(head));
        String[] requestLine = bufferedReader.readLine().split(" ");
        this.method = requestLine[0];
        this.url = requestLine[1];
        this.version = requestLine[2];

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
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String get(String key) {
        String lowerCaseOfKey = key.toLowerCase();
        return headerBody.get(lowerCaseOfKey);
    }
}
