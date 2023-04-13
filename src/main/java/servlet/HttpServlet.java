package servlet;

import annotation.RequestMapping;
import model.HttpRequest;
import model.HttpResponse;

@RequestMapping
public interface HttpServlet {
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}
