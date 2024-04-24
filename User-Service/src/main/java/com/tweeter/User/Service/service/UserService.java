package com.tweeter.User.Service.service;

import com.tweeter.User.Service.entity.User;

public interface UserService {
    boolean isUserExistByLoginId(String loginId);
    boolean isUserExistByEmail(String email);
    User saveUser(User user);
    User updateUser(User user);

    User getUser(String usernameOrEmail);

    String getUserLoginId (Long userId);
}
