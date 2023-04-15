package configure;

import annotation.Bean;
import controller.Controller;
import controller.UserController;
import servlet.HttpDefaultServlet;
import servlet.HttpServlet;
import servlet.HttpServletContainer;
import servlet.HttpUserServlet;
import session.SessionManager;
import util.HttpRequestParser;
import webserver.HttpResponseResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @Bean(name = "httpServletContainer")
    public HttpServletContainer httpServletContainer() {
        return new HttpServletContainer(
                httpServlets(),
                defaultServlet());
    }

    @Bean(name = "httpRequestParser")
    public HttpRequestParser httpRequestParser() {
        return new HttpRequestParser();
    }

    //========
    // TODO : 아래도 빈으로 관리할 수 있는 방법을 생각해보자.
    public List<HttpServlet> httpServlets() {
        List<HttpServlet> httpServlets= new ArrayList<>();

        httpServlets.add(new HttpUserServlet(new UserController(sessionManager())));
        return httpServlets;
    }

    public HttpServlet defaultServlet() {
        return new HttpDefaultServlet();
    }

    public SessionManager sessionManager() {
        return new SessionManager(new HashMap<>());
    }

}
