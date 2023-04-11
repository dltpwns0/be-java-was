package service;

import db.Database;
import model.User;
import util.MyObjectMapper;

public class UserService {

    public void join(Object requestParams) throws Exception {
        User user = MyObjectMapper.readValue(requestParams, User.class)
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 파라미터입니다."));

        Database.addUser(user);
    }
}
