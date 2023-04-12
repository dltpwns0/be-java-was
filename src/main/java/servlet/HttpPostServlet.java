package servlet;

import controller.UserController;
import model.HttpRequest;
import model.HttpResponse;

public class HttpPostServlet implements HttpServlet{
    private final UserController userController = new UserController();

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        doPost(httpRequest, httpResponse);

    }

    private void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        // TODO : 바디가 존재하는 의미가 회원 생성을 의미하지는 않는다. (수정 필요)
        if (httpRequest.hasBody()) {
            userController.createAsPost(httpRequest,httpResponse);
        }
    }

}
