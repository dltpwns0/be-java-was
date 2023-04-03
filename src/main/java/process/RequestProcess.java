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
        String requestUrl = head.getUrl();
        // 파일 존재하는지 체크
        Optional<File> fileOptional = Arrays.stream(basePath)
                .filter(basePath -> {
                    File f = new File(rootPath + basePath + requestUrl);
                    return f.exists();
                })
                .findFirst()
                .map(basePath -> new File((rootPath + basePath + requestUrl)));
        if (fileOptional.isEmpty()) throw new FileNotFoundException();
        File file = fileOptional.get();
        byte[] body = Files.readAllBytes(file.toPath());
        return body;
    }
}
