package interceptor;

import model.User;
import servlet.HttpRequest;
import servlet.HttpResponse;
import session.SessionManager;
import view.ModelAndView;

public class DefaultInterceptor implements Interceptor {

    private SessionManager sessionManager;
    public DefaultInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean preHandle(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView) {
        // 섹션의 값은 user 객체
        User user = (User) sessionManager.getSession(httpRequest);

        if (user == null) {
            modelAndView.addModel("login", false);
            return true;
        }

        modelAndView.addModel("user", user);
        modelAndView.addModel("login", true);
        return true;
    }
}



