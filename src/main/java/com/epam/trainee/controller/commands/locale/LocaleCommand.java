package com.epam.trainee.controller.commands.locale;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.view.Page;
import com.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.epam.trainee.controller.utils.RequestAttributes.LANG;
import static com.epam.trainee.controller.utils.RequestAttributes.REDIRECT_URL;

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
