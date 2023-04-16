package configure;

import annotation.Bean;
import controller.Controller;
import controller.UserController;
import servlet.DispatcherServlet;
import session.SessionManager;
import util.HttpRequestParser;
import view.View;
import view.ViewResolver;
import webserver.HttpResponseResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// TODO : 현재는 싱글톤 처럼 동작하지는 않는다.
public class AppConfigure {

    @Bean(name = "httpResponseResolver")
    public HttpResponseResolver httpResponseResolver() {
        ResolverConfigure resolverConfigure = new ResolverConfigure();
        HttpResponseResolver httpResponseResolver = new HttpResponseResolver();
        // TODO : 바꿔야 함
        resolverConfigure.addMimeType(httpResponseResolver);
        return httpResponseResolver;
    }

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

    public ViewResolver viewResolver() {return new ViewResolver(view());};

    public View view() {return new View();};

}
