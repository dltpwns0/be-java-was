package interceptor;

import servlet.HttpRequest;
import servlet.HttpResponse;
import util.MyHandlerMethod;
import view.ModelAndView;

public interface Interceptor {
    public boolean preHandle(HttpRequest httpRequest, HttpResponse httpResponse, MyHandlerMethod handlerMethod);
}
