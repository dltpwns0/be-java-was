package servlet;

import controller.Controller;
import controller.UserController;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import type.RequestMethod;
import util.RequestMapping;
import view.View;
import view.ViewResolver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;

public class DispatcherServlet implements HttpServlet {

    private final Collection<Controller> controllers;
    private final ViewResolver viewResolver;

    public DispatcherServlet(Collection<Controller> controllers, ViewResolver viewResolver) {
        this.controllers = controllers;
        this.viewResolver = viewResolver;
    }

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        String pathInfo = httpRequest.getPathInfo();
        String requestMethod = httpRequest.getMethod();

        // URL 을 처리할 수 있는 컨트롤러를 찾는다.
        Controller serviceController = RequestMapping.requestControllerMapping(controllers, pathInfo);
        if (serviceController == null) {
            defaultService(httpRequest, httpResponse);
            return;
        }
        // 컨트롤러에서 URL 을 처리할 수 있는 핸들러를 찾는다.
        Method method = RequestMapping.requestHandlerMapping(serviceController.getClass(), pathInfo, RequestMethod.getMethod(requestMethod));
        if (method == null) {
            logger.info("메서드를 찾지 못했습니다.");
            return;
        }

        String viewName = (String) method.invoke(serviceController, httpRequest, httpResponse);

        View resolve = viewResolver.resolve(viewName);

        resolve.render(viewName, httpResponse);

    }

    private void defaultService(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String viewName = httpRequest.getPathInfo();
        View resolve = viewResolver.resolve(viewName);
        resolve.render(viewName, httpResponse);
    }
}
