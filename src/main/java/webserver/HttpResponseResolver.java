package webserver;

import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseResolver {

    Logger logger = LoggerFactory.getLogger(HttpResponseResolver.class);

    private final String rootPath = "src/main/resources/";
    private final String[] basePath = {"static", "templates"};
    private final String welcomePage = "templates/index.html";

    // TODO : 더 효율적인 리다이렉트 방법을 생각해 보자
    private final String redirectPage = "src/main/resources/static/redirectFile.txt";

    private final String CONTENT_TYPE = "Content-Type";

    private final String CONTENT_LENGTH = "Content-Length";

    private final String REQUEST_URL = "Request-URL";

    private final String LOCATION = "Location";

    private static Map<String, String> mime = new HashMap<>();

    public byte[] resolve(HttpResponse response) throws IOException {
        File file = resolveFile(response);

        resolveLocation(response);
        resolveContentLength(response, file);
        resolveContentType(response, file);

        return resolve(response, file);
    }

    private void resolveLocation(HttpResponse response) {
        if (response.getStatus() == HttpResponse.SC_FOUND) {
            response.addHeader(LOCATION, response.getRedirectURL());
        }
    }

    private void resolveContentLength(HttpResponse response, File file) {
        response.addHeader(CONTENT_LENGTH, Long.toString(file.length()));
    }

    private void resolveContentType(HttpResponse response, File file) {
        if (response.containHeader(CONTENT_TYPE)) {
            return;
        }

        // TODO : 아래의 코드가 마음에 들지 않는다. (확장자 명을 이용한 contentType? 이럼 안되~)
        String extension = getExtension(file);
        response.addHeader(CONTENT_TYPE, mime.get(extension));
    }

    private byte[] resolve(HttpResponse httpResponse, File file) throws IOException {
        byte[] body = resolveBody(file);

        byte[] head = resolveHead(httpResponse);

        ByteBuffer buffer = ByteBuffer.allocate(head.length + body.length);
        buffer.put(head);
        buffer.put(body);
        return buffer.array();
    }

    private byte[] resolveHead(HttpResponse httpResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // TODO : 아래의 Reason-Phrase 는 어떻게 하는게 좋을까? ( HTTP 버전은?)
        stringBuilder.append("HTTP/1.1").append(" ").append(httpResponse.getStatus()).append(" ").append(httpResponse.getStatus()).append("\n");

        Collection<String> headersName = httpResponse.getHeadersName();
        for (String name : headersName) {
            stringBuilder.append(name).append(":").append(httpResponse.getHead(name)).append("\n");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString().getBytes();
    }

    public byte[] resolveBody(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private static String getExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    private File resolveFile(HttpResponse response) {
        if (response.getStatus() == HttpResponse.SC_FOUND) {
            return new File(redirectPage);
        }
        String path = response.getHead(REQUEST_URL);
        return getFileAt(path);
    }

    private File getFileAt(String requestUrl) {
        return Arrays.stream(basePath)
                .filter(basePath -> fileExistsAt(rootPath + basePath + requestUrl))
                .findFirst()
                .map(basePath -> new File((rootPath + basePath + requestUrl)))
                .orElse(new File(rootPath + welcomePage));
    }

    private boolean fileExistsAt(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return false;
        }
        return f.isFile();
    }

    public void addSupportedMimeType(String extension, String mimeType) {
        mime.put(extension, mimeType);
    }
}
