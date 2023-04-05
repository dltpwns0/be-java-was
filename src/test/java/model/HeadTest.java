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
    @DisplayName("HttpRequestHead 클래스의 생성자가 String 타입의 헤더를 입력받아, 요청라인을 파싱하는지 테스트")
    public void HttpRequestLineTest() throws IOException {
        // when
        sb.append("GET /index.html?userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");

        // given
        head = new HttpRequestHead(sb.toString());

        // then
        assertThat(head.getMethod()).isEqualTo("GET");
        assertThat(head.getUrl()).isEqualTo("/index.html");
        assertThat(head.getRequestParam()).isEqualTo("userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com");
        assertThat(head.getVersion()).isEqualTo("HTTP1.1");
    }

    @Test
    @DisplayName("HttpRequestHead 클래스의 생성자가 String 타입의 헤더를 입력받아, MIME 헤더를 파싱하는지 테스트")
    public void HttpRequestMIMETest() throws IOException {
        // when
        sb.append("GET /index.html?userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");

        // given
        head = new HttpRequestHead(sb.toString());

        // then
        assertThat(head.getHeaderElement("accept")).isEqualTo("*/*");
        assertThat(head.getHeaderElement("Content-Type")).isEqualTo("text");
    }
}
