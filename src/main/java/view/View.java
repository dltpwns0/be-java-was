package view;

import com.google.common.net.MediaType;
import model.HttpResponse;
import session.Cookie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;

import static webserver.RequestHandler.logger;

public class View {

    private final String SET_COOKIE = "Set-Cookie";

    public void view(File file, HttpResponse httpResponse) throws IOException {
        byte[] body = resolveBody(file);
        byte[] head = resolveHead(httpResponse);

        ByteBuffer buffer = ByteBuffer.allocate(head.length + body.length);
        buffer.put(head);
        buffer.put(body);

        OutputStream outputStream = httpResponse.getOutputStream();

        outputStream.write(buffer.array());
        outputStream.flush();
    }

    private byte[] resolveHead(HttpResponse httpResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // TODO : 아래의 Reason-Phrase 는 어떻게 하는게 좋을까? ( HTTP 버전은?)
        stringBuilder.append("HTTP/1.1").append(" ").append(httpResponse.getStatus()).append(" ").append(httpResponse.getStatus()).append("\n");

        // 응답 헤더 추가
        resolveHeader(httpResponse, stringBuilder);

        // 응답 쿠키 추가
        resolveCookie(httpResponse, stringBuilder);

        stringBuilder.append("\n");

        return stringBuilder.toString().getBytes();
    }

    private static void resolveHeader(HttpResponse httpResponse, StringBuilder stringBuilder) {
        Collection<String> headersName = httpResponse.getHeadersName();
        for (String name : headersName) {
            stringBuilder.append(name).append(":").append(httpResponse.getHead(name)).append("\n");
        }
    }

    private void resolveCookie(HttpResponse httpResponse, StringBuilder stringBuilder) {
        Collection<Cookie> cookies = httpResponse.getCookies();
        for (Cookie cookie : cookies) {
            stringBuilder.append(SET_COOKIE).append(":").append(cookie).append("\n");
        }
    }

    public byte[] resolveBody(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }
}
