package controller;

import annotation.RequestMapping;
import servlet.HttpRequest;
import servlet.HttpResponse;
import model.User;
import service.UserService;
import session.SessionManager;
import type.RequestMethod;
import view.ModelAndView;

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
    public String create(HttpRequest httpRequest) throws Exception {
        String requestUrl = httpRequest.getPathInfo();
        Map<String, String> requestParams = httpRequest.getRequestBody();
        userService.join(requestParams);
        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpRequest httpRequest, HttpResponse httpResponse,
                        ModelAndView modelAndView) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        Optional<User> user = userService.login(userId, password);

        if (user.isEmpty()) {
            return "redirect:/user/login_failed.html";
        }
        sessionManager.createSession(user.get(),httpResponse);
        modelAndView.addModel("user", user.get());
        return "redirect:/";
    }

    @RequestMapping()
    public String show(HttpRequest httpRequest, ModelAndView modelAndView) {

        User user = (User)sessionManager.getSession(httpRequest);
        if (user != null) {
            modelAndView.addModel("user", user);
        }
        return httpRequest.getPathInfo();
    }
}
