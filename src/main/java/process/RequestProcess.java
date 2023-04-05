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

        // TODO : GET 이외의 요청 메소드는 처리하지 않음. (기능 추가 필요)
        return null;
    }

    private byte[] getRequestFileAsByte() throws IOException {
        String requestUrl = head.getUrl().split("\\?")[0];

        Optional<File> fileOptional = getFileAt(requestUrl);

        File file = fileOptional.orElse(new File(rootPath + welcomePage));
        return Files.readAllBytes(file.toPath());
    }

    private Optional<File> getFileAt(String requestUrl) {
        return Arrays.stream(basePath)
                .filter(basePath -> fileExistsAt(requestUrl, basePath))
                .findFirst()
                .map(basePath -> new File((rootPath + basePath + requestUrl)));
    }

    private boolean fileExistsAt(String requestUrl, String basePath) {
        File f = new File(rootPath + basePath + requestUrl);
        return f.exists();
    }
}
