package com.epam.trainee.service.impl;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.UserDao;
import com.epam.trainee.model.entities.User;
import com.epam.trainee.service.UserService;

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
    public User registerUser(User user) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}
