package ua.task1.trainee.service.impl;

import ua.task1.trainee.model.dao.DaoFactory;
import ua.task1.trainee.model.dao.UserDao;
import ua.task1.trainee.model.entities.User;
import ua.task1.trainee.model.exceptions.AuthenticationException;
import ua.task1.trainee.model.exceptions.MissingEntityException;
import ua.task1.trainee.service.UserService;
import ua.task1.trainee.model.exceptions.ExceptionMessageAttributes;

import java.util.Objects;

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
            throw new AuthenticationException((User) e.getEntity(), ExceptionMessageAttributes.REGISTRATION_FAILED);
        }
    }

    private void throwIfUserExists(User user) throws AuthenticationException {
        if (userDao.contains(user)) {
            throw new AuthenticationException(user, ExceptionMessageAttributes.DUPLICATED_USER);
        }
    }

    private void checkUserRoles(User user) throws AuthenticationException {
        if (user.getRoles() == null || user.getRoles().size() == 0) {
            throw new AuthenticationException(user, ExceptionMessageAttributes.NO_ROLE);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        Objects.requireNonNull(email, "Email can't be null");
        return userDao.getUserByEmail(email);
    }
}
