package ua.task1.trainee.controller.commands.authentication;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.model.exceptions.ExceptionMessageAttributes;
import ua.task1.trainee.controller.utils.validators.SignInValidator;
import ua.task1.trainee.model.entities.Role;
import ua.task1.trainee.model.entities.User;
import ua.task1.trainee.model.exceptions.AuthenticationException;
import ua.task1.trainee.model.exceptions.MissingEntityException;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;
import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Set;
import java.util.stream.Collectors;

@WebUrl("/login")
public class SignInCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute(RequestAttributes.AUTHENTICATION) != null) {
            return Page.HOME;
        }
        req.setAttribute(RequestAttributes.ROLES, Role.values());
        return Page.LOGIN;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        Set<String> invalid = new SignInValidator(req).validate();

        try {
            if (invalid.isEmpty()) {
                User user = tryLogin(req);
                HttpSession session = req.getSession();
                session.removeAttribute(RequestAttributes.AUTHENTICATION);
                session.setAttribute(RequestAttributes.AUTHENTICATION, user);
                System.out.println(user + " successful authenticated");
                return Page.HOME;
            } else {
                //TODO: fix it. Could check in jsp every field that invalid and take messages from Resources Bundle
                req.setAttribute(RequestAttributes.INVALID, invalid.stream()
                        .collect(Collectors.joining("is invalid,", "", "is invalid")));
                return Page.LOGIN;
            }
        } catch (AuthenticationException e) {
            req.setAttribute(RequestAttributes.INVALID, e.getMessage());
            System.out.println("Authentication failed. user: " + e.getUser());
            return Page.LOGIN;
        }
    }

    private User tryLogin(HttpServletRequest req) throws AuthenticationException {
        String email = req.getParameter(RequestAttributes.EMAIL);
        try {
            User user = ServiceFactory.getInstance().getUserService().findUserByEmail(email);
            if(user.getPassword().equals(req.getParameter(RequestAttributes.PASSWORD))) {
                return user;
            }
        } catch (MissingEntityException e) {
            System.out.println("User with email: " + email + " not found");
        }
        throw new AuthenticationException(ExceptionMessageAttributes.AUTH_FAILED);
    }
}
