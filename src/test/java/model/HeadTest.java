package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

public class HeadTest {
    Head head;
    @BeforeEach
    public void beforeEach() throws IOException {

    }
    @Test
    @DisplayName("String 타입의 헤더를 입력받으면, Head 객체로 저장이 잘된다.")
    public void HeadParseTest() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("GET /index.html HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");

        head = new Head(sb.toString());

        assertThat(head.getMethod()).isEqualTo("GET");
        assertThat(head.getUrl()).isEqualTo("/index.html");
        assertThat(head.getVersion()).isEqualTo("HTTP1.1");
        assertThat(head.get("accept")).isEqualTo("*/*");
        assertThat(head.get("Content-Type")).isEqualTo("text");

    }
}
