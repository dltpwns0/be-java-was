package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MyObjectMapperByString {

    public static Optional<?> readValue(String requestParams, Class<?> userClass) throws Exception {// 사실 userClass 를 입력 받을 필요는 없지만, 공부를 위해 넣어 봤다.
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
