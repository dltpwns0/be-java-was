package controller;

import model.HttpRequest;
import model.HttpResponse;
import service.UserService;
import java.io.IOException;

public class UserController {

    private final UserService userService = new UserService();

    public void create(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
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
