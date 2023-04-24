package interceptor;

import servlet.HttpRequest;
import servlet.HttpResponse;
import session.SessionManager;
import util.MyHandlerMethod;

public class DefaultInterceptor implements Interceptor {

    private SessionManager sessionManager;
    public DefaultInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean preHandle(HttpRequest httpRequest, HttpResponse httpResponse, MyHandlerMethod handlerMethod) {
        return true;
    }
}



