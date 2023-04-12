package servlet;

import model.HttpRequest;
import model.HttpResponse;

public interface HttpServlet {
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}
