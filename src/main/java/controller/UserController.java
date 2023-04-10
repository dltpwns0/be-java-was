package controller;

import model.HttpRequest;
import model.HttpResponse;
import service.UserService;
import java.io.IOException;

public class UserController {

    private final UserService userService = new UserService();

    public void create(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String requestUrl = httpRequest.getUrl();
        String requestParams = httpRequest.getRequestParam();
        userService.join(requestParams);
        httpResponse.doGet(requestUrl);
    }

    public void show(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String requestUrl = httpRequest.getUrl();
        httpResponse.doGet(requestUrl);
    }
}
