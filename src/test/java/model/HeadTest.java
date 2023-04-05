package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

public class HeadTest {
    StringBuilder sb;
    HttpRequestHead head;
    @BeforeEach
    public void beforeEach() throws IOException {
        sb = new StringBuilder();

    }
    @Test
    @DisplayName("String 타입의 헤더를 입력받으면, HttpRequestHead 객체로 저장이 잘된다.")
    public void HeadParseTest() throws IOException {
        // when
        sb.append("GET /index.html HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");

        // given
        head = new HttpRequestHead(sb.toString());

        // then
        assertThat(head.getMethod()).isEqualTo("GET");
        assertThat(head.getUrl()).isEqualTo("/index.html");
        assertThat(head.getVersion()).isEqualTo("HTTP1.1");
        assertThat(head.getHeaderElement("accept")).isEqualTo("*/*");
        assertThat(head.getHeaderElement("Content-Type")).isEqualTo("text");
    }

    @Test
    @DisplayName("HttpRequestHead 클래스가 요청 파라미터를 잘 파싱해야 한다.")
    public void requestParameterParsTest() throws IOException {
        // when
        sb.append("GET /index.html?userId=cire&password=1234&name=leeDongjun&email=dltpwns6@naver.com HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");

        // given
        head = new HttpRequestHead(sb.toString());

        // then
        assertThat(head.getMethod()).isEqualTo("GET");
        assertThat(head.getUrl()).isEqualTo("/index.html?userId=cire&password=1234&name=leeDongjun&email=dltpwns6@naver.com");
        assertThat(head.getVersion()).isEqualTo("HTTP1.1");
        assertThat(head.getHeaderElement("accept")).isEqualTo("*/*");
        assertThat(head.getHeaderElement("Content-Type")).isEqualTo("text");

        assertThat(head.getRequestParam("userId")).isEqualTo("cire");
        assertThat(head.getRequestParam("password")).isEqualTo("1234");
        assertThat(head.getRequestParam("name")).isEqualTo("leeDongjun");
        assertThat(head.getRequestParam("email")).isEqualTo("dltpwns6@naver.com");
    }
}
