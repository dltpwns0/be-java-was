package util;


import model.Model;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

class MyLittleMustacheTest {

    MyLittleMustache myLittleMustache;
    Interpreter interpreter;

    @BeforeEach
    public void beforeEach() {
        interpreter = new Interpreter();
        myLittleMustache = new MyLittleMustache(interpreter);
    }
    @Test
    @DisplayName("객체 안의 메소드를 실행한 결과가 렌더링 되어야 한다.")
    public void mustacheTest0() {
        User user = new User("idA", "1234", "lee", "dltpwns6@naver.com");
        Model model = new Model("user", user);
        interpreter.addModel(model);
        String html = myLittleMustache.render(htmlTest0);

        System.out.println(html);
    }

    @Test
    @DisplayName("조건문이 참일 때, 조건문 태그 안의 문서를 렌더링 해야한다.")
    public void mustacheTest1() {
        User user = new User("idA", "1234", "lee", "dltpwns6@naver.com");

        Model model = new Model("user", user);
        interpreter.addModel(model);
        model = new Model("isTrue", true);
        interpreter.addModel(model);

        String html = myLittleMustache.render(htmlTest1);

        System.out.println(html);
    }

    @Test
    @DisplayName("조건문이 거짓일 때, 조건문 태그 안의 문서를 렌더링 하지 말아야한다.")
    public void mustacheTest2() {
        User user = new User("idA", "1234", "lee", "dltpwns6@naver.com");

        Model model = new Model("user", user);
        interpreter.addModel(model);
        model = new Model("isTrue", false);
        interpreter.addModel(model);

        String html = myLittleMustache.render(htmlTest2);

        System.out.println(html);

    }

    @Test
    @DisplayName("객체가 null 일 때, 태그 안의 문서를 렌더링 하지 말아야한다. ")
    public void mustacheTest3() {
        User user = new User("idA", "1234", "lee", "dltpwns6@naver.com");

        Model model = new Model("user", user);
        interpreter.addModel(model);
        model = new Model("isTrue", null);
        interpreter.addModel(model);

        String html = myLittleMustache.render(htmlTest3);

        System.out.println(html);

    }

    @Test
    @DisplayName("반복문 태그를 만났을 때, 반복적으로 객체의 메서드의 실행한 결과가 렌더링 되어야 한다.")
    public void mustacheTest4() {
        User userA = new User("idA", "1234", "lee", "dltpwns1@naver.com");
        User userB = new User("idB", "13", "Dong", "dong@google.com");
        User userC = new User("idC", "1234", "Jun", "lee@nate.com");

        Collection<User> users = new ArrayList<>();
        users.add(userA);
        users.add(userB);
        users.add(userC);

        Model userModel = new Model("user", users);

        interpreter.addModel(userModel);
        String html = myLittleMustache.render(htmlTest4);

        System.out.println(html);

    }

    static String htmlTest0 = """
                            <li>{{user.getUserId}}</li>
                            <li>{{user.getName}}</li>
                            <li>{{user.getEmail}}</li>
                            """;

    static String htmlTest1 = """
                            {{#isTrue}}
                            <li>{{user.getUserId}}</li>
                            <li>{{user.getName}}</li>
                            <li>{{user.getEmail}}</li>
                            {{/isTrue}}
                            """;

    static String htmlTest2 = """
                            {{#isTrue}}
                            <li>{{user.getUserId}}</li>
                            <li>{{user.getName}}</li>
                            <li>{{user.getEmail}}</li>
                            {{/isTrue}}
                            """;

    static String htmlTest3 = """
                            {{#NULL}}
                            <li>{{user.getUserId}}</li>
                            <li>{{user.getName}}</li>
                            <li>{{user.getEmail}}</li>
                            {{/NULL}}
                            """;
    static String htmlTest4 = """
                            {{#user}}
                            <li>{{getUserId}}</li>
                            <li>{{getName}}</li>
                            <li>{{getEmail}}</li>
                            {{/user}}
                            """;
}