package ua.task1.trainee.controller.commands.authentication;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;
import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebUrl("/logout")
public class LogOutCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(RequestAttributes.AUTHENTICATION);
        }
        return Page.HOME;
    }
}
