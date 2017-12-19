package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.User;

public interface UserDao {

    User createUser(User user);

    User getUserByEmail(String email);

    boolean contains(User user);
}
