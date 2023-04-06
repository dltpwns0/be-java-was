package process;
import db.Database;
import model.HttpRequestHead;
import model.User;
import util.MyObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

public class RequestProcess {

    private final String rootPath = "src/main/resources/";
    private final String[] basePath = {"static", "templates"};
    private final String welcomePage = "templates/index.html";
    
    public byte[] makeResponse(String request) throws Exception {
        HttpRequestHead requestHead = new HttpRequestHead(request);

        String requestMethod = requestHead.getMethod();
        // TODO : 메소드의 분리가 필요하다
        if (requestMethod.equalsIgnoreCase("GET")) {
            if (!requestHead.hasRequestParam()) {
                String requestParams = requestHead.getRequestParam();
                Optional<?> optionalObject = MyObjectMapper.readValue(requestParams, User.class);
                if (optionalObject.isPresent()) {
                    Object object = optionalObject.get();
                    if (object instanceof User) {
                        User user = (User) object;
                        Database.addUser(user);
                    }
                }
            }
            return getRequestFileAsByte(requestHead);
        }

        // TODO : GET 이외의 요청 메소드는 처리하지 않음. (기능 추가 필요)
        return null;
    }

    private byte[] getRequestFileAsByte(HttpRequestHead requestHead) throws IOException {
        String requestUrl = requestHead.getUrl();

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
