package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.view.Page;
import com.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("/locale")
public class LocaleCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        String locale = req.getParameter("locale");
        req.getSession().removeAttribute("locale");
        req.getSession().setAttribute("locale", locale);
        return Page.valueOf(String.valueOf(req.getSession().getAttribute("page")));
    }
}
