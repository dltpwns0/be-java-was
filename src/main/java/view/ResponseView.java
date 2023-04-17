package view;

import model.HttpResponse;
import session.Cookie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResponseView implements View{

    private String name;

    private final String ROOT_PATH = "src/main/resources/";
    private final String[] BASE_PATH = {"static", "templates"};
    private final String WELCOME_PAGE = "templates/index.html";
    private final String SET_COOKIE = "Set-Cookie";

    private final String CONTENT_TYPE = "Content-Type";

    private final String CONTENT_LENGTH = "Content-Length";

    private static final int NULL_STATUS_CODE = 0;

    private static Map<String, String> mime = new HashMap<>();

    public ResponseView(String name) {
        this.name = name;

        // TODO : MIME 타입을 초기화하는 것을 다시 해보자 (나중에)
        mime.put("html", "text/html");
        mime.put("css", "text/css");
        mime.put("js", "application/javascript");
        mime.put("png", "image/png");
        mime.put("ico", "image/x-icon");
    }

    public String getName() {
        return name;
    }

    public void render(String viewName, HttpResponse httpResponse) throws IOException {
        File file = getFileAt(viewName);
        byte[] body = renderBody(file);
        byte[] head = renderHead(httpResponse, file);

        ByteBuffer buffer = ByteBuffer.allocate(head.length + body.length);
        buffer.put(head);
        buffer.put(body);

        OutputStream outputStream = httpResponse.getOutputStream();

        outputStream.write(buffer.array());
        outputStream.flush();
    }

    private byte[] renderHead(HttpResponse httpResponse, File file) {
        StringBuilder stringBuilder = new StringBuilder();

        // TODO : 아래의 Reason-Phrase 는 어떻게 하는게 좋을까? ( HTTP 버전은?)
        renderStateLine(httpResponse, stringBuilder);

        // 응답 헤더 추가
        renderHeader(httpResponse, stringBuilder);

        // 응답 컨텐츠 길이 추가
        resolveContentLength(httpResponse, file);

        // 응답 컨텐츠 타입 추가
        resolveContentType(httpResponse, file);

        // 응답 쿠키 추가
        renderCookie(httpResponse, stringBuilder);

        stringBuilder.append("\n");

        return stringBuilder.toString().getBytes();
    }
    private static void renderStateLine(HttpResponse httpResponse, StringBuilder stringBuilder) {
        int statusCode = httpResponse.getStatus();
        if (statusCode == NULL_STATUS_CODE) {
            statusCode = HttpResponse.SC_OK;
        }
        stringBuilder.append("HTTP/1.1").append(" ").append(statusCode).append(" ").append(statusCode).append("\n");
    }

    private static void renderHeader(HttpResponse httpResponse, StringBuilder stringBuilder) {
        Collection<String> headersName = httpResponse.getHeadersName();
        for (String name : headersName) {
            stringBuilder.append(name).append(":").append(httpResponse.getHead(name)).append("\n");
        }
    }

    private void renderCookie(HttpResponse httpResponse, StringBuilder stringBuilder) {
        Collection<Cookie> cookies = httpResponse.getCookies();
        for (Cookie cookie : cookies) {
            stringBuilder.append(SET_COOKIE).append(":").append(cookie).append("\n");
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

    public byte[] renderBody(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private File getFileAt(String requestUrl) {
        for (String basePath : BASE_PATH) {
            String filePath = ROOT_PATH + basePath + requestUrl;
            if (!fileExistsAt(filePath)) {
                continue;
            }
            return new File(filePath);
        }
        return new File(ROOT_PATH + WELCOME_PAGE);
    }

    private boolean fileExistsAt(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return false;
        }
        return f.isFile();
    }

    private static String getExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }
}
