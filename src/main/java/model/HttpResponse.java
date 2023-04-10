package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

public class HttpResponse {

    private final String rootPath = "src/main/resources/";
    private final String[] basePath = {"static", "templates"};
    private final String welcomePage = "templates/index.html";

    public byte[] responseHead = null;
    public byte[] responseBody = null;

    public byte[] getResponseBody() {
        return this.responseBody;
    }

    public void doGet(String requestUrl) throws IOException {
        Optional<File> fileOptional = getFileAt(requestUrl);
        File file = fileOptional.orElse(new File(rootPath + welcomePage));
        this.responseBody = Files.readAllBytes(file.toPath());
    }

    private Optional<File> getFileAt(String requestUrl) {
        return Arrays.stream(basePath)
                .filter(basePath -> fileExistsAt(requestUrl, basePath))
                .findFirst()
                .map(basePath -> new File((rootPath + basePath + requestUrl)));
    }

    private boolean fileExistsAt(String requestUrl, String basePath) {
        File f = new File(rootPath + basePath + requestUrl);
        if (!f.exists()) {
            return false;
        }
        return f.isFile();
    }

}
