package servlet;

import model.HttpRequest;
import model.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpRequestParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

class HttpServletContainerTest {

    StringBuilder sb;
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    HttpRequestParser httpRequestParser;

    HttpServletContainer httpServletContainer;

    @BeforeEach
    public void beforeEach() throws IOException {
        sb = new StringBuilder();
        httpRequestParser = new HttpRequestParser();
        httpResponse = null;

        httpServletContainer = new HttpServletContainer();
    }

    @Test
    @DisplayName("HttpRequest 요청을 처리할 수 있는 서블릿을 반환해야한다.")
    public void httpServletContainerTest() throws IOException {
        // when
        sb.append("GET /user/create?userId=cire&password=1234&name=이동준&email=dltpwns6@naver.com HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");
        BufferedReader br = new BufferedReader(new StringReader(sb.toString()));
        httpRequest = httpRequestParser.parse(br);

        // given

        Object actualHttpServlet = httpServletContainer.getServlet(httpRequest);

        // then

        assertThat(actualHttpServlet).isInstanceOf(HttpUserServlet.class);
    }

    @Test
    @DisplayName("HttpRequest 요청을 처리할 수 있는 서블릿을 반환해야한다.")
    public void httpServletContainerTest2() throws IOException {
        // when
        sb.append("GET / HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");
        BufferedReader br = new BufferedReader(new StringReader(sb.toString()));
        httpRequest = httpRequestParser.parse(br);

        // given

        Object actualHttpServlet = httpServletContainer.getServlet(httpRequest);

        // then

        assertThat(actualHttpServlet).isInstanceOf(HttpDefaultServlet.class);
    }

    @Test
    @DisplayName("HttpRequest 요청을 처리할 수 있는 서블릿을 반환해야한다.")
    public void httpServletContainerTest3() throws IOException {
        // when
        sb.append("GET /css/styles.css HTTP1.1").append("\n");
        sb.append("Accept: */*").append("\n");
        sb.append("Content-Type: text").append("\n");
        sb.append("\n");
        BufferedReader br = new BufferedReader(new StringReader(sb.toString()));
        httpRequest = httpRequestParser.parse(br);

        // given

        Object actualHttpServlet = httpServletContainer.getServlet(httpRequest);

        // then

        assertThat(actualHttpServlet).isInstanceOf(HttpDefaultServlet.class);
    }
}
