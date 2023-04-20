package controller;

import annotation.RequestMapping;
import model.Article;
import org.checkerframework.checker.units.qual.A;
import service.ArticleService;
import servlet.HttpRequest;
import view.ModelAndView;

import java.util.Collection;

@RequestMapping(path = "/")
public class DefaultController implements Controller {

    private ArticleService articleService;

    public DefaultController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping()
    public String show(HttpRequest httpRequest, ModelAndView modelAndView) {
        // TODO : 이러면 안될 것 같다. URL 과 핸들러를 맵핑 하는 방법을 다시 생각하자 (search가 아닌 table 방식으로)
        if (httpRequest.getPathInfo().equals("/index.html") || httpRequest.getPathInfo().equals("/")) {
            return showList(modelAndView);
        }

        return httpRequest.getPathInfo();
    }
    public String showList(ModelAndView modelAndView) {
        Collection<Article> all = articleService.findAll();
        modelAndView.addModel("articles", all);
        return "/index.html";
    }
}
