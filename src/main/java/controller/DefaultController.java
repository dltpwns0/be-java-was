package controller;

import annotation.RequestMapping;
import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;

@RequestMapping
public class DefaultController implements Controller{

    @RequestMapping
    public void show(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestUrl = httpRequest.getPathInfo();
        httpResponse.setPath(requestUrl);
    }
}
