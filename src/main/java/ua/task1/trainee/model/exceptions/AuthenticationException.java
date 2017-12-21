package ua.task1.trainee.model.exceptions;

import ua.task1.trainee.model.entities.User;

public class AuthenticationException extends Exception {

    private User user;

    public AuthenticationException(User user) {
        this.user = user;
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(User user, String message) {
        super(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
