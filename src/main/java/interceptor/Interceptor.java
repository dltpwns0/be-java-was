package interceptor;

import servlet.HttpRequest;
import servlet.HttpResponse;
import view.ModelAndView;

public interface Interceptor {
    public boolean preHandle(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView);
}
