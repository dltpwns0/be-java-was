package controller;

import model.HttpRequest;
import model.HttpResponse;
import service.UserService;
import java.io.IOException;
import java.util.Map;

public class UserController {

    private final UserService userService = new UserService();

    // TODO : 어노테이션을 이용해서 POST 로 온 요청인지 아닌지 알 수 있는 방법이 있을 것이다!
    public void createAsPost(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String requestUrl = httpRequest.getPath();
        Map<String, String> requestParams = httpRequest.getRequestBody();
        userService.join(requestParams);
        httpResponse.redirect("/");
    }

    public void createAsGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String requestUrl = httpRequest.getPath();
        String requestParams = httpRequest.getQuery();
        userService.join(requestParams);
        httpResponse.redirect("/");
    }

    public void show(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String requestUrl = httpRequest.getPath();
        httpResponse.setPath(requestUrl);
    }
}
