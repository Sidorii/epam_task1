package com.epam.trainee.controller.commands.authentication;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.utils.WebUrl;
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
import java.util.Collections;
import java.util.Set;

import static com.epam.trainee.controller.utils.RequestAttributes.*;

@WebUrl("/registration")
public class SignUpCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        return Page.LOGIN;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Set<String> invalidParams = new SignUpValidator(req).validate();

            if (invalidParams.isEmpty()) {
                processRegistration(req);
                return Page.HOME;
            } else {
                req.setAttribute("invalid", invalidParams);
                return Page.LOGIN;
            }

        } catch (AuthenticationException e) {
            req.setAttribute("invalid", Collections.singletonList(e.getMessage()));
            return Page.LOGIN;
        } catch (NullPointerException e) {
            e.printStackTrace();
            req.setAttribute("invalid", Collections.singletonList("Some data required but not set"));
            return Page.LOGIN;
        }
    }

    private void processRegistration(HttpServletRequest req) {
        User user = new User(
                req.getParameter(NAME),
                req.getParameter(EMAIL),
                req.getParameter(PASSWORD));
        user.addRole(Role.ADMIN);

        UserService userService = ServiceFactory.getInstance().getUserService();
        User authorized = userService.registerUser(user);
        req.getSession().setAttribute(AUTHENTICATION, authorized.getRoles());
    }
}
