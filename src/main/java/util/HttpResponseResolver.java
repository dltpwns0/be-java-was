package util;

import model.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class HttpResponseResolver {

    private final String rootPath = "src/main/resources/";
    private final String[] basePath = {"static", "templates"};
    private final String welcomePage = "templates/index.html";

    private static Map<String, String> mime = new HashMap<>();

    public byte[] resolve(HttpResponse response) throws IOException {
        File file = resolveFile(response);
        String contentType = resolveContentType(response, file);

        return resolve(file, contentType);
    }

    public void addSupportedMimeType(String extension, String mimeType) {
        mime.put(extension, mimeType);
    }

    private byte[] resolve(File file, String contentType) throws IOException {
        byte[] body = resolveBody(file);
        byte[] head = resolveHead(contentType, body.length);

        ByteBuffer buffer = ByteBuffer.allocate(head.length + body.length);
        buffer.put(head);
        buffer.put(body);
        return buffer.array();
    }

    private File resolveFile(HttpResponse response) {
        String path = response.getPath();
        return getFileAt(path);
    }

    private String resolveContentType(HttpResponse response, File file) {
        if (response.getContentType() != null) {
            return response.getContentType();
        }
        String extension = getExtension(file);
        return  mime.get(extension);
    }

    private static String getExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        return  fileName.substring(index + 1);
    }

    private byte[] resolveHead(String contentType, int lengthOfBodyContent) {
        StringBuilder stringBuilder = new StringBuilder();

        // TODO : 왜 항상 200 OK? (예외 처리 로직 추가)
        stringBuilder.append("HTTP/1.1 200 OK)").append("\n");
        stringBuilder.append("Content-Type: ").append(contentType).append("\n");
        stringBuilder.append("Content-Length: ").append(lengthOfBodyContent).append("\n");
        stringBuilder.append("\n");
        return stringBuilder.toString().getBytes();
    }

    public byte[] resolveBody(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private File getFileAt(String requestUrl) {
        return Arrays.stream(basePath)
                .filter(basePath -> fileExistsAt(requestUrl, basePath))
                .findFirst()
                .map(basePath -> new File((rootPath + basePath + requestUrl)))
                .orElse(new File(rootPath + welcomePage));
    }

    private boolean fileExistsAt(String requestUrl, String basePath) {
        File f = new File(rootPath + basePath + requestUrl);
        if (!f.exists()) {
            return false;
        }
        return f.isFile();
    }
}
