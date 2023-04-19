package controller;

import annotation.RequestMapping;
import model.User;
import servlet.HttpRequest;
import servlet.HttpResponse;
import view.ModelAndView;

@RequestMapping(path = "/")
public class DefaultController implements Controller {
    @RequestMapping()
    public String show(HttpRequest httpRequest) {
        return httpRequest.getPathInfo();
    }
}
