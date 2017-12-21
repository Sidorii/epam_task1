package ua.task1.trainee.controller.commands.locale;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static ua.task1.trainee.controller.utils.RequestAttributes.LANG;
import static ua.task1.trainee.controller.utils.RequestAttributes.REDIRECT_URL;

@WebUrl("/locale")
public class LocaleCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        String locale = Optional.ofNullable(req.getParameter(LANG))
                .orElse(req.getLocale().toString());
        req.getSession().removeAttribute(LANG);
        req.getSession().setAttribute(LANG, locale);
        String redirect = Optional.ofNullable(req.getParameter(REDIRECT_URL))
                .orElse("/");
        return Page.HOME;
    }
}
