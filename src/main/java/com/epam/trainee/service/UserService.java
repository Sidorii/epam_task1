package com.epam.trainee.service;

import com.epam.trainee.model.entities.User;

public interface UserService {

    User registerUser(User user);

    User findUserByEmail(String email);
}
