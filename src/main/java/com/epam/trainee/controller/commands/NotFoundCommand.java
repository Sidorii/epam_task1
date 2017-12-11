package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundCommand implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        return Page.NOT_FOUND;
    }
}
