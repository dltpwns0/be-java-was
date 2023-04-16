package controller;

import annotation.RequestMapping;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import service.UserService;
import session.SessionManager;
import type.RequestMethod;

import java.util.Map;
import java.util.Optional;

@RequestMapping(path = "/user")
public class UserController implements Controller {

    private final UserService userService = new UserService();
    private SessionManager sessionManager;

    public UserController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String create(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String requestUrl = httpRequest.getPathInfo();
        Map<String, String> requestParams = httpRequest.getRequestBody();
        userService.join(requestParams);
        httpResponse.sendRedirect("/");
        httpResponse.setStatus(HttpResponse.SC_SEE_OTHER);
        return null;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> requestBody = httpRequest.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        // TODO : Optional 을 사용하는게 좋을까?
        Optional<User> user = userService.login(userId, password);

        if (user.isEmpty()) {
            httpResponse.setStatus(HttpResponse.SC_UNAUTHORIZED);
            return "/user/login_failed.html";
        }
        sessionManager.createSession(user.get(),httpResponse);
        return "/";
    }

    @RequestMapping()
    public String show(HttpRequest httpRequest, HttpResponse httpResponse) {
        return httpRequest.getPathInfo();
    }
}
