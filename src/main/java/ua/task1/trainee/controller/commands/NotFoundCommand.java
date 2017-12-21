package ua.task1.trainee.controller.commands;

import ua.task1.trainee.view.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundCommand implements Command {

    @Override
    public Page executeGet(HttpServletRequest req, HttpServletResponse resp) {
        return Page.NOT_FOUND;
    }
}
