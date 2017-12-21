package com.epam.trainee.controller.commands.authentication;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.model.exceptions.ExceptionMessageAttributes;
import com.epam.trainee.controller.utils.validators.SignUpValidator;
import com.epam.trainee.model.entities.Role;
import com.epam.trainee.model.entities.User;
import com.epam.trainee.model.exceptions.AuthenticationException;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.service.UserService;
import com.epam.trainee.view.Page;
import com.epam.trainee.view.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.trainee.controller.utils.RequestAttributes.*;

@WebUrl("/registration")
public class SignUpCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute(AUTHENTICATION) != null) {
            return Page.HOME;
        }
        req.setAttribute(ROLES, Role.values());
        return Page.LOGIN;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            return registerNewUser(req);
        } catch (AuthenticationException e) {
            req.setAttribute(INVALID, e.getMessage());
            return Page.LOGIN;
        }
    }

    private View registerNewUser(HttpServletRequest req) throws AuthenticationException {
        Set<String> invalidParams =
                new SignUpValidator(req).validate();

        if (invalidParams.isEmpty()) {
            processRegistration(req);
            return Page.HOME;
        } else {
            //TODO: fix it. Could check in jsp every field that invalid and take messages from Resources Bundle
            String message = invalidParams.stream()
                    .collect(Collectors.joining(" is invalid,", "", " is invalid"));
            req.setAttribute(INVALID, message);
            req.setAttribute(ROLES, Role.values());
            return Page.LOGIN;
        }
    }

    private void processRegistration(HttpServletRequest req) throws AuthenticationException {
        User user = new User(
                req.getParameter(NAME),
                req.getParameter(EMAIL),
                req.getParameter(PASSWORD));
        String[] roles = req.getParameterValues(ROLES);
        System.out.println(user.getName());
        try {
            for (String role : roles) {
                user.addRole(Role.valueOf(role));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new AuthenticationException(user, ExceptionMessageAttributes.UNKNOWN_ROLE);
        }

        UserService userService = ServiceFactory.getInstance().getUserService();
        User authorized = userService.registerUser(user);
        req.getSession().setAttribute(AUTHENTICATION, authorized);
    }
}
