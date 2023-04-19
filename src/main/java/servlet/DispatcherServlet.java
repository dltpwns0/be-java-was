package servlet;

import interceptor.Interceptor;
import controller.Controller;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.MethodAdaptor;
import util.MyHandlerMethod;
import view.View;
import view.ModelAndView;
import view.ViewResolver;

import java.util.Collection;

public class DispatcherServlet implements HttpServlet {

    private final Collection<Controller> controllers;
    private final Collection<Interceptor> interceptors;
    private final ViewResolver viewResolver;
    private final MethodAdaptor methodAdaptor;

    public DispatcherServlet(Collection<Controller> controllers, ViewResolver viewResolver, Collection<Interceptor> interceptors) {
        this.controllers = controllers;
        this.viewResolver = viewResolver;
        this.interceptors = interceptors;


        // DI 주입 받을 수도 있다. (나중에)
        this.methodAdaptor = new MethodAdaptor();

    }

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        // 요청에 맞는 컨트롤러와 메소드를 찾는다.
        MyHandlerMethod handlerMethod = new MyHandlerMethod(httpRequest, controllers);

        ModelAndView modelAndView = new ModelAndView();

        for (Interceptor interceptor : interceptors) {
            interceptor.preHandle(httpRequest, httpResponse, modelAndView);
        }

        String viewName = methodAdaptor.handle(httpRequest, httpResponse, modelAndView, handlerMethod);
        
        View view = viewResolver.resolve(viewName);

        view.render(modelAndView.getModel(), httpResponse);
    }

}
