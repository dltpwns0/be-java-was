package servlet;

import controller.UserController;
import model.HttpRequest;
import model.HttpResponse;

public class HttpGetServlet implements HttpServlet {
    private final UserController userController = new UserController();

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        doGet(httpRequest, httpResponse);

    }


    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        if (httpRequest.getQueryString() != null) {
            userController.createAsGet(httpRequest, httpResponse);
            return;
        }
        userController.show(httpRequest, httpResponse);
    }
}
