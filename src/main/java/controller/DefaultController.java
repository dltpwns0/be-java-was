package controller;

import annotation.RequestMapping;
import servlet.HttpRequest;

@RequestMapping(path = "/")
public class DefaultController implements Controller {
    @RequestMapping()
    public String show(HttpRequest httpRequest) {
        return httpRequest.getPathInfo();
    }
}
