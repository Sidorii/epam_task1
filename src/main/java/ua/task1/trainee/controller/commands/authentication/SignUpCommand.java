package ua.task1.trainee.controller.commands.authentication;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.controller.utils.validators.SignUpValidator;
import ua.task1.trainee.model.entities.Role;
import ua.task1.trainee.model.entities.User;
import ua.task1.trainee.model.exceptions.AuthenticationException;
import ua.task1.trainee.model.exceptions.ExceptionMessageAttributes;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.service.UserService;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;
import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;

@WebUrl("/registration")
public class SignUpCommand implements Command {

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
        try {
            return registerNewUser(req);
        } catch (AuthenticationException e) {
            req.setAttribute(RequestAttributes.INVALID, e.getMessage());
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
            req.setAttribute(RequestAttributes.INVALID, message);
            req.setAttribute(RequestAttributes.ROLES, Role.values());
            return Page.LOGIN;
        }
    }

    private void processRegistration(HttpServletRequest req) throws AuthenticationException {
        User user = new User(
                req.getParameter(RequestAttributes.NAME),
                req.getParameter(RequestAttributes.EMAIL),
                req.getParameter(RequestAttributes.PASSWORD));
        String[] roles = req.getParameterValues(RequestAttributes.ROLES);
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
        req.getSession().setAttribute(RequestAttributes.AUTHENTICATION, authorized);
    }
}
