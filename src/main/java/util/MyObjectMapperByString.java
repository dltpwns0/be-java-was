package util;

import java.util.*;

public class MyObjectMapperByString {

    public static Optional<?> readValue(String requestParams, Class<?> userClass) throws Exception {
        Map<String, String> requestParamMap = parseRequestParams(requestParams);

        return MyObjectMapperByMap.readValue(requestParamMap, userClass);
    }

    private static Map<String, String> parseRequestParams(String requestParams) {
        Map<String, String> requestParamMap = new LinkedHashMap<>();
        String[] params = requestParams.split("&");
        for (String param : params) {
            String[] keyAndVal = param.split("=");
            requestParamMap.put(keyAndVal[0], keyAndVal[1]);
        }
        return requestParamMap;
    }
}
