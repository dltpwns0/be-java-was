package configure;

import annotation.Bean;
import controller.Controller;
import controller.UserController;
import org.checkerframework.checker.units.qual.C;
import servlet.DispatcherServlet;
import session.SessionManager;
import util.HttpRequestParser;
import view.RedirectView;
import view.ResponseView;
import view.View;
import view.ViewResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// TODO : 현재는 싱글톤 처럼 동작하지는 않는다.
public class AppConfiguration {

    @Bean(name = "httpRequestParser")
    public HttpRequestParser httpRequestParser() {
        return new HttpRequestParser();
    }

    @Bean(name = "dispatcherServlet")
    public DispatcherServlet dispatcherServlet() {
        Collection<Controller> controllers = new ArrayList<>();
        controllers.add(new UserController(sessionManager()));

        return new DispatcherServlet(
                controllers,
                viewResolver());
    }

    //========
    // TODO : 아래도 빈으로 관리할 수 있는 방법을 생각해보자.
    public SessionManager sessionManager() {
        return new SessionManager(new HashMap<>());
    }
    public ViewResolver viewResolver() {
        Collection<View> views = new ArrayList<>();

        // TODO : 아래의 순서를 지켜줘야 하는 문제가 있음
        views.add(new RedirectView("redirect:/"));
        views.add(new ResponseView("/"));
        return new ViewResolver(views);
    }
}
