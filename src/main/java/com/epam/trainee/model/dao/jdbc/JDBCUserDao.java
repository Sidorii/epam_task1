package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.UserDao;
import com.epam.trainee.model.entities.User;

public class JDBCUserDao implements UserDao {

    private static final JDBCUserDao INSTANCE = new JDBCUserDao();

    public static JDBCUserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean contains(User user) {
        return false;
    }
}
