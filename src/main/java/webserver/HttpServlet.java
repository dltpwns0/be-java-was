package webserver;

import controller.UserController;
import model.HttpRequest;
import model.HttpResponse;
import util.HttpMethod;

public class HttpServlet {

    private final UserController userController = new UserController();

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        doRequestMethod(httpRequest, httpResponse);

    }

    private void doRequestMethod(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        HttpMethod method = httpRequest.getMethod();
        if (method == HttpMethod.GET) {
            doGet(httpRequest, httpResponse);
        }
        if (method == HttpMethod.POST);
            doPost(httpRequest, httpResponse);
        // TODO : 요청 메서드가 더 있을 수 있다.
    }

    private void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {

        // TODO : 바디가 존재하는 의미가 회원 생성을 의미하지는 않는다. (수정 필요)
        if (httpRequest.hasBody()) {
            userController.createAsPost(httpRequest,httpResponse);
        }
    }

    private void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        if (httpRequest.hasQuery()) {
            userController.createAsGet(httpRequest, httpResponse);
            return;
        }
        userController.show(httpRequest, httpResponse);
    }

}
