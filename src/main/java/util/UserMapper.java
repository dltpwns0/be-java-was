package util;

import model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class UserMapper {

    public static Optional<User> readValue(String requestParams, Class<User> userClass) throws Exception {// 사실 userClass 를 입력 받을 필요는 없지만, 공부를 위해 넣어 봤다.

        // 리플렉션 API 를 통해 private 필드를 가져오는 것은 좋지 않다고 한다. 그렇다면 다른 방법으로 가져올 수 있나?
        Map<String, String> params = parseRequestParams(requestParams);
        List<Object> constructVariables = new ArrayList<>();
        List<Class<?>> constructType = new ArrayList<>();

        // TODO : 아래의 방법은 뭔가.... 많이 비효율적인 것 같다. (효율성 개선 필요) (어려울 뜻)
        // 그냥 요청 파라미터를 입력으로 받고 객체로 변환하는 메소드가 없을까? (있을 것 같다...) (있넹;;)
        // TODO : 요청 파라미터의 순서와 생성자 매개변수의 순서가 맞아야 하는 문제가 있다. 어떻게 해결할 수 있는 방법이 있을까? (매우 어려울 것 같다..)
        // TODO : 예외 처리를 잘해야 한다. (예외 처리 추가 필요)
        for (Map.Entry<String, String> param: params.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();

            Field fieldName = userClass.getDeclaredField(key);
            Class<?> fieldType = fieldName.getType();

            constructVariables.add(fieldType.cast(value)); // 여기에는 생성자 변수들이 들어간다.
            constructType.add(fieldType);
        }

        Constructor<User> constructor = userClass.getConstructor(constructType.toArray(Class<?>[]::new));
        return Optional.of(constructor.newInstance(constructVariables.toArray()));
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
