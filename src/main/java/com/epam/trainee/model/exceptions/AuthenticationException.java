package com.epam.trainee.model.exceptions;

import com.epam.trainee.model.entities.User;

public class AuthenticationException extends RuntimeException {

    private User user;

    public AuthenticationException(User user) {
        this.user = user;
    }

    public AuthenticationException(User user,String message) {
        super(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
