package servlet;

import annotation.RequestMapping;
import controller.DefaultController;
import model.HttpRequest;
import model.HttpResponse;

@RequestMapping(path = "/")
public class HttpDefaultServlet implements HttpServlet {
    private final DefaultController defaultController = new DefaultController();

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        String pathInfo = httpRequest.getPathInfo();
        String requestMethod = httpRequest.getMethod();

        defaultController.show(httpRequest, httpResponse);

    }

}
