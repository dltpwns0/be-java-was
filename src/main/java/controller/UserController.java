package controller;

import annotation.RequestMapping;
import model.HttpRequest;
import model.HttpResponse;
import service.UserService;
import type.RequestMethod;
import java.util.Map;

@RequestMapping(path = "/user")
public class UserController implements Controller{

    private final UserService userService = new UserService();

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void createAsPost(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String requestUrl = httpRequest.getPathInfo();
        Map<String, String> requestParams = httpRequest.getRequestBody();
        userService.join(requestParams);
        httpResponse.redirect("/");
    }
    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public void createAsGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String requestUrl = httpRequest.getPathInfo();
        String requestParams = httpRequest.getQueryString();
        userService.join(requestParams);
        httpResponse.redirect("/");
    }

    @RequestMapping
    public void show(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestUrl = httpRequest.getPathInfo();
        httpResponse.setPath(requestUrl);
    }
}
