package com.epam.trainee.service;

import com.epam.trainee.model.entities.User;
import com.epam.trainee.model.exceptions.AuthenticationException;

public interface UserService {

    User registerUser(User user) throws AuthenticationException;

    User findUserByEmail(String email);
}
