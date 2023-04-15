package servlet;

import annotation.RequestMapping;
import controller.Controller;
import controller.UserController;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import type.RequestMethod;
import util.RequestHandlerMapping;

import java.lang.reflect.Method;

@RequestMapping(path = "/user")
public class HttpUserServlet implements HttpServlet {

    private final Controller userController;

    public HttpUserServlet(Controller controller) {
        this.userController = controller;
    }

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        String pathInfo = httpRequest.getPathInfo();
        String requestMethod = httpRequest.getMethod();

        Method method = RequestHandlerMapping.requestHandlerMapping(UserController.class, pathInfo, RequestMethod.getMethod(requestMethod));

        if (method == null) {
            logger.info("메서드를 찾지 못했습니다.");
            return;
        }

        method.invoke(userController, httpRequest, httpResponse);
    }
}
