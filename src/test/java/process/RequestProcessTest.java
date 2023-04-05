package process;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
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
    public void HeadParseTest() throws IOException, FileNotFoundException {

        String head = sb.toString();
        RequestProcess requestProcess = new RequestProcess();

        byte[] body = requestProcess.process(head);
        byte[] expectedBody = Files.readAllBytes(new File("src/main/resources/templates/index.html").toPath());

        assertThat(body).isEqualTo(expectedBody);
    }

}
