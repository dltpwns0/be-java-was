package util;

import controller.Controller;
import servlet.HttpRequest;
import servlet.HttpResponse;
import view.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodAdaptor {

    private MyArgumentResolver myArgumentResolver;

    public MethodAdaptor(MyArgumentResolver myArgumentResolver) {
        this.myArgumentResolver = myArgumentResolver;
    }

    public String handle(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView, MyHandlerMethod handlerMethod) throws InvocationTargetException, IllegalAccessException {
        Controller handler = handlerMethod.getHandler();
        Method method = handlerMethod.getMethod();

        List<Object> arguments = myArgumentResolver.resolve(httpRequest, httpResponse, modelAndView, handlerMethod);

        String viewName = (String) method.invoke(handler, arguments.toArray());
        httpResponse.sendRedirect(viewName);
        return viewName;
    }

}
