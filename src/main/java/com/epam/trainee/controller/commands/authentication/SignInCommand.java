package com.epam.trainee.controller.commands.authentication;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.controller.utils.validators.SignInValidator;
import com.epam.trainee.model.entities.Role;
import com.epam.trainee.model.entities.User;
import com.epam.trainee.model.exceptions.AuthenticationException;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.view.Page;
import com.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.trainee.controller.utils.RequestAttributes.AUTHENTICATION;
import static com.epam.trainee.controller.utils.RequestAttributes.EMAIL;
import static com.epam.trainee.controller.utils.RequestAttributes.PASSWORD;

@WebUrl("/login")
public class SignInCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute(AUTHENTICATION) != null) {
            return Page.HOME;
        }
        req.setAttribute("roles", Role.values());
        return Page.LOGIN;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        Set<String> invalid = new SignInValidator(req).validate();

        try {
            if (invalid.isEmpty()) {
                User user = registerUser(req)
                        .orElseThrow(() ->
                                new AuthenticationException("Authentication failed. Invalid email or password"));
                req.getSession().setAttribute(AUTHENTICATION, user.getRoles());
                System.out.println(user + " successful authenticated");
                return Page.HOME;
            } else {
                req.setAttribute("invalid", invalid.stream()
                        .collect(Collectors.joining("is invalid,", "", "is invalid")));
                return Page.LOGIN;
            }
        } catch (AuthenticationException e) {
            req.setAttribute("invalid", e.getMessage());
            System.out.println("Authentication failed. user: " + e.getUser());
            return Page.LOGIN;
        }
    }

    private Optional<User> registerUser(HttpServletRequest req) {
        String email = req.getParameter(EMAIL);
        User user = ServiceFactory.getInstance().getUserService().findUserByEmail(email);
        return Optional.ofNullable(user.getPassword().equals(req.getParameter(PASSWORD)) ? user : null);
    }
}
