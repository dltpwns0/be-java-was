package servlet;

import annotation.RequestMapping;
import model.HttpRequest;

import java.util.ArrayList;
import java.util.List;

public class HttpServletContainer {

    private final HttpServlet defaultServlet;
    private final List<HttpServlet> servlets;

    // TODO : 설정 파일로 초기화 할 수 있는 방법을 다시 생각해보자! (힌트 : https://lucas.codesquad.kr/masters-2023/course/backend-java/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC/Dependency-Injection)
    public HttpServletContainer(List<HttpServlet> servlets, HttpServlet defaultServlet) {
        this.servlets = servlets;
        this.defaultServlet = defaultServlet;
//        this.defaultServlet = new HttpDefaultServlet();
    }

    public HttpServlet getServlet(HttpRequest httpRequest) {
        String pathInfo = httpRequest.getPathInfo();

        for (HttpServlet servlet : servlets) {
            RequestMapping requestAnnotation = servlet.getClass().getAnnotation(RequestMapping.class);

            String[] servletPaths = requestAnnotation.path();
            for (String servletPath : servletPaths) {
                if (pathInfo.startsWith(servletPath)) {
                    return servlet;
                }
            }
        }
        return defaultServlet;
    }
}
