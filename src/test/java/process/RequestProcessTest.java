package process;

import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.MyObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RequestProcessTest {

    private static StringBuilder sb;

    @BeforeAll
    public static void beforeAll() {
        sb = new StringBuilder();
        sb.append("GET /index.html HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");
    }

    @Test
    @DisplayName("GET 요청이 왔을 때, 적절한 파일을 읽어 와야한다.")
    public void HeadParseTest() throws Exception {

        String head = sb.toString();
        RequestProcess requestProcess = new RequestProcess();

        byte[] body = requestProcess.makeResponse(head);
        byte[] expectedBody = Files.readAllBytes(new File("src/main/resources/templates/index.html").toPath());

        assertThat(body).isEqualTo(expectedBody);
    }

    @Test
    @DisplayName("String 타입의 요청 파라미터를 입력으로 받았을때, Optional<User> 타입의 객체를 만들어야 한다.")
    public void UserMapperTest() throws Exception {
        // given
        String requestParams = "userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com";

        // when
        Optional<User> userOptional = (Optional<User>) MyObjectMapper.readValue(requestParams, User.class);
        User user = userOptional.get();

        // then
        assertThat(user.getUserId()).isEqualTo("cire");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getName()).isEqualTo("이동준");
        assertThat(user.getEmail()).isEqualTo("dltpwns6@naver.com");

        assertThat(user.getUserId()).isNotEqualTo("cirae");
        assertThat(user.getPassword()).isNotEqualTo("134");
        assertThat(user.getName()).isNotEqualTo("이준");
        assertThat(user.getEmail()).isNotEqualTo("dltpwns6@snaver.com");
    }


}
