package ua.task1.trainee.model.dao;

import ua.task1.trainee.model.entities.User;

public interface UserDao {

    User createUser(User user);

    User getUserByEmail(String email);

    boolean contains(User user);
}
