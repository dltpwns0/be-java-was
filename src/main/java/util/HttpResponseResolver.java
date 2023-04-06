package util;

import model.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

public class HttpResponseResolver {

    private final String rootPath = "src/main/resources/";
    private final String[] basePath = {"static", "templates"};
    private final String welcomePage = "templates/index.html";


    public byte[] resolve(HttpResponse response) throws IOException {

        resolveContentType(response);

        String path = response.getPath();
        String contentType = response.getContentType();

        byte[] body = resolveBody(path);
        byte[] head = resolveHead(contentType, body.length);

        ByteBuffer buffer = ByteBuffer.allocate(head.length + body.length);
        buffer.put(head);
        buffer.put(body);
        return buffer.array();
    }

    private static void resolveContentType(HttpResponse response) {
        if (response.getContentType() != null) {
            return;
        }
        String path = response.getPath();
        int index = path.lastIndexOf(".");
        String extension = path.substring(index + 1);
        if (extension.equals("html")) {
            response.setContentType("text/html");
        }
        if (extension.equals("css")) {
            response.setContentType("text/css");
        }
        if (extension.equals("js")) {
            response.setContentType("application/javascript");
        }
        if (extension.equals("png")) {
            response.setContentType("image/png");
        }
        if (extension.equals("ico")) {
            response.setContentType("image/x-icon");
        }
    }

    private byte[] resolveHead(String contentType, int lengthOfBodyContent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK)").append("\n");
        stringBuilder.append("Content-Type: ").append(contentType).append("\n");
        stringBuilder.append("Content-Length: ").append(lengthOfBodyContent).append("\n");
        stringBuilder.append("\n");
        return stringBuilder.toString().getBytes();
    }

    public byte[] resolveBody(String path) throws IOException {
        Optional<File> fileOptional = getFileAt(path);
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
        if (!f.exists()) {
            return false;
        }
        return f.isFile();
    }
}
