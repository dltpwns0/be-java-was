package controller;

import annotation.RequestMapping;
import model.HttpRequest;
import model.HttpResponse;
import service.UserService;
import type.RequestMethod;

import java.util.Map;

@RequestMapping(path = "/user")
public class UserController implements Controller {

    private final UserService userService = new UserService();

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void create(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        String requestUrl = httpRequest.getPathInfo();
        Map<String, String> requestParams = httpRequest.getRequestBody();
        userService.join(requestParams);
        httpResponse.sendRedirect("/user/success-create-id");
    }

    @RequestMapping()
    public void show(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestUrl = httpRequest.getPathInfo();
        httpResponse.addHeader("Request-URL", requestUrl);
    }

    // TODO  : 상태 코드를 바꿀려고, 이렇게 핸들러까지 추가 해야하나? (더 좋은 방법이 있을 것이다!)
    @RequestMapping(path = "/success-create-id")
    public void createSuccess(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setStatus(HttpResponse.SC_CREATE);
    }
}
