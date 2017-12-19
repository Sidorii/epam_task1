package com.epam.trainee.service.impl;

import com.epam.trainee.model.dao.UserDao;
import com.epam.trainee.model.entities.Role;
import com.epam.trainee.model.entities.User;
import com.epam.trainee.model.exceptions.AuthenticationException;
import com.epam.trainee.model.exceptions.MissingEntityException;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class UserServiceImplTest {

    private UserDao userDao;
    private UserServiceImpl userService;
    private User user;
    private Role role;

    @Before
    public void setUp() {
        userDao = createMock(UserDao.class);
        userService = UserServiceImpl.getInstance();
        userService.setUserDao(userDao);
        role = Role.ADMIN;
        user = new User("name", "email", "password");
        user.addRole(role);
    }

    @Test
    public void testRegisterUser() {
        User storedUser = new User(user.getName(), user.getEmail(), user.getPassword());
        storedUser.setId(1);

        expect(userDao.contains(user))
                .andReturn(false);
        expect(userDao.createUser(user))
                .andReturn(storedUser);
        replay(userDao);

        User result = userService.registerUser(user);
        assertEquals(result, storedUser);
        verify(userDao);
    }

    @Test(expected = AuthenticationException.class)
    public void testRegisterUserThatExits() {
        expect(userDao.contains(user))
                .andReturn(true);
        replay(userDao);
        userService.registerUser(user);
        verify(userDao);
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNullUser() {
        userService.registerUser(null);
    }

    @Test(expected = AuthenticationException.class)
    public void testRegisterUserWithoutRoles() {
        user.removeRole(role);
        expect(userDao.contains(user))
        .andReturn(false);
        replay(userDao);
        userService.registerUser(user);
        verify(userDao);
    }

    @Test
    public void testFindUserByEmail() {
        expect(userDao.getUserByEmail(user.getEmail()))
                .andReturn(user);
        replay(userDao);

        User result = userService.findUserByEmail(user.getEmail());
        assertEquals(user, result);
        verify(userDao);
    }

    @Test(expected = MissingEntityException.class)
    public void testShouldCannotFindUserByWrongEmail() {
        expect(userDao.getUserByEmail(user.getEmail()))
                .andThrow(new MissingEntityException("No user found with email '" + user.getEmail() + "'"));
        replay(userDao);
        userService.findUserByEmail(user.getEmail());
        verify(userDao);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldThrowNullPointerWhenTryToFindUserByNullEmail() {
        userService.findUserByEmail(null);
    }
}