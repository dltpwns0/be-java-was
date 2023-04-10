package util;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MyObjectMapper {

    public static Optional<?> readValue(Object requestParams, Class<?> userClass) throws Exception {// 사실 userClass 를 입력 받을 필요는 없지만, 공부를 위해 넣어 봤다.
        if (requestParams instanceof Map<?, ?>) {
            // TODO : 언체크 예외 처리를 해야하나?
            return MyObjectMapperByMap.readValue((Map<String, String>) requestParams, userClass);
        }
        if (requestParams instanceof String) {
            return MyObjectMapperByString.readValue((String) requestParams, userClass);
        }
        return Optional.empty();
    }
}
