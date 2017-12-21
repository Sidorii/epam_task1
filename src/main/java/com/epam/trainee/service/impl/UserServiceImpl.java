package com.epam.trainee.service.impl;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.UserDao;
import com.epam.trainee.model.entities.User;
import com.epam.trainee.model.exceptions.AuthenticationException;
import com.epam.trainee.model.exceptions.MissingEntityException;
import com.epam.trainee.service.UserService;

import java.util.Objects;

import static com.epam.trainee.model.exceptions.ExceptionMessageAttributes.*;

public class UserServiceImpl implements UserService {

    private static final UserServiceImpl INSTANCE = new UserServiceImpl();

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = DaoFactory.getInstance().getUserDao();
    }


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User registerUser(User user) throws AuthenticationException {
        Objects.requireNonNull(user, "Register user can't be null");
        throwIfUserExists(user);
        checkUserRoles(user);
        try {
            return userDao.createUser(user);
        } catch (MissingEntityException e) {
            throw new AuthenticationException((User) e.getEntity(), REGISTRATION_FAILED);
        }
    }

    private void throwIfUserExists(User user) throws AuthenticationException {
        if (userDao.contains(user)) {
            throw new AuthenticationException(user, DUPLICATED_USER);
        }
    }

    private void checkUserRoles(User user) throws AuthenticationException {
        if (user.getRoles() == null || user.getRoles().size() == 0) {
            throw new AuthenticationException(user, NO_ROLE);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        Objects.requireNonNull(email, "Email can't be null");
        return userDao.getUserByEmail(email);
    }
}
