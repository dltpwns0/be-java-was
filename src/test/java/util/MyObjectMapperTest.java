package util;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MyObjectMapperTest {

    @Test
    @DisplayName("유저의 데이터 들어온다면, 유저 클래스를 만들어야 한다.")
    public void MyObjectMapperTest() throws Exception {
        // given
        Map<String, String> userDataAsMap = new LinkedHashMap<>();
        userDataAsMap.put("userId", "cire");
        userDataAsMap.put("password", "1234");
        userDataAsMap.put("name", "leeDongjun");
        userDataAsMap.put("email", "dltpwns6@naver.com");

        String userDataAsString = "userId=cire&password=1234&name=leeDongjun&email=dltpwns6@naver.com";

        // when
        User expectedUserOfMap = (User) MyObjectMapperByMap.readValue(userDataAsMap, User.class).get();
        User expectedUserOfString = (User) MyObjectMapperByString.readValue(userDataAsString, User.class).get();
        User actualUserOfMap = (User) MyObjectMapper.readValue(userDataAsMap, User.class).get();
        User actualUserOfString = (User) MyObjectMapper.readValue(userDataAsString, User.class).get();

        // then
        Assertions.assertThat(actualUserOfMap).usingRecursiveComparison().isEqualTo(expectedUserOfMap);
        Assertions.assertThat(actualUserOfString).usingRecursiveComparison().isEqualTo(expectedUserOfString);
    }
}
