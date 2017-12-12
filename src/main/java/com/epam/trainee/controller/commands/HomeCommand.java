package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.view.Page;
import com.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebUrl("/")
public class HomeCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        return Page.HOME;
    }
}
