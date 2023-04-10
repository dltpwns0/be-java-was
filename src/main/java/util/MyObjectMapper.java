package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MyObjectMapper {

    public static Optional<?> readValue(String requestParams, Class<?> userClass) throws Exception {// 사실 userClass 를 입력 받을 필요는 없지만, 공부를 위해 넣어 봤다.
        List<String> params = parseRequestParams(requestParams);

        // TODO : 현재의 객체의 생성자를 찾는 방법은 파라미터의 개수만을 이용하여 찾는다.
        // TODO : 생성자 매개변수의 이름도 사용할 수 없을까?
        Constructor<?> constructor = findConstructor(userClass, params);
        if (constructor == null) {
            return Optional.empty();
        }

        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> constructVariables = convertType(params, parameterTypes);

        return getObject(userClass, constructVariables, constructor);
    }

    private static List<Object> convertType(List<String> params, Class<?>[] parameterTypes) {
        List<Object> constructVariables = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            String parameter = params.get(i);
            Class<?> parameterType = parameterTypes[i];
            constructVariables.add(parameterType.cast(parameter));
        }
        return constructVariables;
    }

    private static Optional<?> getObject(Class<?> userClass, List<Object> constructVariables, Constructor<?> constructor) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return Optional.of(userClass.cast(constructor.newInstance(constructVariables.toArray())));
    }

    private static Constructor<?> findConstructor(Class<?> userClass, List<String> params) {
        Constructor<?> constructor = null;
        for (Constructor<?> cont : userClass.getConstructors()) {
            if (cont.getParameterTypes().length != params.size()) continue;
            constructor = cont;
        }
        return constructor;
    }

    private static List<String> parseRequestParams(String requestParams) {
        List<String> requestParamMap = new ArrayList<>();
        String[] params = requestParams.split("&");
        for (String param : params) {
            String[] keyAndVal = param.split("=");
            requestParamMap.add(keyAndVal[1]);
        }
        return requestParamMap;
    }
}
