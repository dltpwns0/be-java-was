package view;

import model.HttpResponse;
import session.Cookie;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public class RedirectView implements View{
    private String name;
    private final String SET_COOKIE = "Set-Cookie";
    private final String LOCATION = "Location";

    private final String REDIRECT = "redirect:";

    private static final int NULL_STATUS_CODE = 0;

    public RedirectView(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void render(String viewName, HttpResponse httpResponse) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        // TODO : 아래의 Reason-Phrase 는 어떻게 하는게 좋을까? ( HTTP 버전은?)
        renderStateLine(httpResponse, stringBuilder);

        // 응답 Location 추가
        renderLocation(viewName, stringBuilder);

        // 응답 헤더 추가
        renderHeader(httpResponse, stringBuilder);

        // 응답 쿠키 추가
        renderCookie(httpResponse, stringBuilder);

        stringBuilder.append("\n");

        OutputStream outputStream = httpResponse.getOutputStream();
        outputStream.write(stringBuilder.toString().getBytes());
    }

    private static void renderStateLine(HttpResponse httpResponse, StringBuilder stringBuilder) {
        int statusCode = httpResponse.getStatus();
        if (statusCode == NULL_STATUS_CODE) {
            statusCode = HttpResponse.SC_FOUND;
        }
        stringBuilder.append("HTTP/1.1").append(" ").append(statusCode).append(" ").append(statusCode).append("\n");
    }

    private void renderLocation(String viewName, StringBuilder stringBuilder) {
        viewName = viewName.replace(REDIRECT,"");
        stringBuilder.append(LOCATION).append(":").append(viewName).append("\n");
    }

    private void renderHeader(HttpResponse httpResponse, StringBuilder stringBuilder) {
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
}
