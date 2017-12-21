package ua.task1.trainee.service;

import ua.task1.trainee.model.entities.User;
import ua.task1.trainee.model.exceptions.AuthenticationException;

public interface UserService {

    User registerUser(User user) throws AuthenticationException;

    User findUserByEmail(String email);
}
