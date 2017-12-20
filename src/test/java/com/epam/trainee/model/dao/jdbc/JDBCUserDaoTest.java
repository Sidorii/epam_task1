package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.entities.Role;
import com.epam.trainee.model.entities.User;
import com.epam.trainee.model.exceptions.AuthenticationException;
import com.epam.trainee.model.exceptions.MissingEntityException;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class JDBCUserDaoTest {

    private JDBCUserDao userDao;
    private User user;
    private Set<Role> roles;
    private Connection connection;

    @BeforeClass
    public static void init() throws SQLException {
        InputStream is = ClassLoader.getSystemResourceAsStream("schema.sql");
        try (Connection connection = JDBCUserDao.getInstance().getConnection()) {
            RunScript.execute(connection, new InputStreamReader(is));
        }
    }

    @Before
    public void setUp() {
        userDao = JDBCUserDao.getInstance();
        connection = userDao.getConnection();
        user = new User("name", "email", "password");
        roles = new HashSet<>();
        roles.add(Role.ADMIN);
        user.setRoles(roles);
    }

    @Test
    public void testCreateUser() throws AuthenticationException {
        User res = userDao.createUser(user);
        assertEquals(user, res);
        assertNotNull(res.getId());
    }

    @Test
    public void testGetUserByEmail() throws AuthenticationException {
        userDao.createUser(user);
        User result = userDao.getUserByEmail(user.getEmail());
        assertEquals(user, result);
    }

    @Test(expected = MissingEntityException.class)
    public void testShouldNotFindUserByWrongEmailAndThrowException() {
        userDao.getUserByEmail("wrong mail");
    }

    @Test
    public void testContains() throws AuthenticationException {
        user.setId(1);
        assertFalse(userDao.contains(user));
        userDao.createUser(user);
        assertTrue(userDao.contains(user));
    }

    @After
    public void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        final String cleanDependencies = "DELETE FROM task1.user_role";
        final String cleanQuery = "DELETE FROM task1.users";
        statement.executeUpdate(cleanDependencies);
        statement.executeUpdate(cleanQuery);
        connection.commit();
        statement.close();
        connection.close();
    }
}