package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.User;
import com.epam.trainee.model.exceptions.AuthenticationException;

public interface UserDao {

    User createUser(User user);

    User getUserByEmail(String email);

    boolean contains(User user);
}
