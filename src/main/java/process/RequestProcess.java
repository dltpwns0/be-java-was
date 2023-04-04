package process;

import model.Head;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

public class RequestProcess {

    private final String rootPath = "src/main/resources/";
    private final String[] basePath = {"static", "templates"};
    private final String welcomePage = "templates/index.html";
    private Head head;

    public byte[] process(String request) throws FileNotFoundException, IOException {
        this.head = new Head(request);

        String method = head.getMethod();
        if (method.equalsIgnoreCase("GET")) {
            return getRequestFileAsByte();
        }
        return null;
    }

    private byte[] getRequestFileAsByte() throws IOException {
        String requestUrl = head.getUrl().split("\\?")[0];

        // TODO : 코드를 하나도 알아볼 수 없다. 수정 필요 (메소드 분리)
        Optional<File> fileOptional = Arrays.stream(basePath)
                .filter(basePath -> findFile(requestUrl, basePath))
                .findFirst()
                .map(basePath -> new File((rootPath + basePath + requestUrl)));

        // 요청 파일이 없다면 웰컴 페이지 리턴
        File file = fileOptional.orElse(new File(rootPath + welcomePage));
        byte[] body = Files.readAllBytes(file.toPath());
        return body;
    }

    private boolean findFile(String requestUrl, String basePath) {
        File f = new File(rootPath + basePath + requestUrl);
        return f.exists();
    }
}
