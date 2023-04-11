package util;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MyObjectMapperByStringTest {

    @Test
    @DisplayName("String 타입의 유저 데이터가 들어온다면, 유저 클래스를 만들어야 한다.")
    public void MyObjectMapperByStringTest() throws Exception {
        // given
        String userData = "userId=cire&password=1234&name=leeDongjun&email=dltpwns6@naver.com";

        // when
        Optional<User> userOptional = (Optional<User>) MyObjectMapperByString.readValue(userData, User.class);
        User user = userOptional.get();

        // then
        Assertions.assertThat(user.getUserId()).isEqualTo("cire");
        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
        Assertions.assertThat(user.getName()).isEqualTo("leeDongjun");
        Assertions.assertThat(user.getEmail()).isEqualTo("dltpwns6@naver.com");

        Assertions.assertThat(user.getUserId()).isNotEqualTo("이것은 잘못!");


    }

}