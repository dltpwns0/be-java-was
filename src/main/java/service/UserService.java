package service;

import db.Database;
import model.User;
import util.MyObjectMapper;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class UserService {

    public void join(Object requestParams) throws Exception {
        User user = MyObjectMapper.readValue(requestParams, User.class)
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청 파라미터입니다."));

        Database.addUser(user);
    }

    public User login(String userId, String password) {
        return Optional.of(Database.findUserById(userId))
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }

}
