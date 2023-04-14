package util;

import model.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class HttpRequestParser {

    private final Integer METHOD_INDEX = 0;
    private final Integer PATH_INDEX = 1;
    private final Integer QUERY_INDEX = 2;

    public HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        HttpRequest request = new HttpRequest();

        parseRequestLine(request, bufferedReader);
        parseRequestHeaders(request, bufferedReader);
        parseBody(request, bufferedReader);

        return request;
    }

    private void parseRequestLine(HttpRequest request, BufferedReader bufferedReader) throws IOException {
        String[] requestLine = bufferedReader.readLine().split(" ");
        request.setMethod(requestLine[METHOD_INDEX]);
        set(request, requestLine[PATH_INDEX]);
        request.setVersion(requestLine[QUERY_INDEX]);
    }

    private void set(HttpRequest request, String pathInfo) {
        String decodedUrl = URLDecoder.decode(pathInfo, StandardCharsets.UTF_8);
        if (decodedUrl.contains("?")) {
            String[] pathAndQuery = decodedUrl.split("\\?");
            request.setPathInfo(pathAndQuery[0]);
            request.setQueryString(pathAndQuery[1]);
            return;
        }
        request.setPathInfo(decodedUrl);
    }

    private void parseRequestHeaders(HttpRequest request, BufferedReader bufferedReader) throws IOException {
        Map<String, String> requestHeaders = new HashMap<>();
        String line = null;
        while (!(line = bufferedReader.readLine()).equals("")) {
            // TODO 헤더 바디에서, 하나의 헤더 요소의 값들은 1개 이상일 수 있다. 수정 필요

            List<String> headerElement = Arrays.stream(line.split(":"))
                    .map(String::trim)
                    .collect(Collectors.toList());

            requestHeaders.put(headerElement.get(0), headerElement.get(1));
        }
        request.requestHeaders(requestHeaders);
    }

    private void parseBody(HttpRequest request, BufferedReader bufferedReader) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength <= 0) {
            return;
        }

        // TODO : 클린 코드가 절실한 상황!
        Map<String, String> requestBody = new LinkedHashMap<>();
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);

        String bodyOfHeader = URLDecoder.decode(new String(body), StandardCharsets.UTF_8);
        String[] bodyElement = bodyOfHeader.split("&");
        for (String pair : bodyElement) {
            String[] keyAndValue = pair.split("=");
            requestBody.put(keyAndValue[0], keyAndValue[1]);
        }
        request.setRequestBody(requestBody);
    }
}
