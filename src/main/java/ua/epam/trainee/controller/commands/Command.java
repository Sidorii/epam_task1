package ua.epam.trainee.controller.commands;

import ua.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    default View execute(HttpServletRequest req, HttpServletResponse resp) {
        switch (req.getMethod()) {
            case "GET":
                return executeGet(req, resp);
            case "POST":
                return executePost(req, resp);
            default:
                throw new IllegalStateException("Unsupported method");
        }
    }

    default View executeGet(HttpServletRequest req, HttpServletResponse resp){
        throw new IllegalStateException("Unsupported method");
    }

    default View executePost(HttpServletRequest req, HttpServletResponse resp) {
        throw new IllegalStateException("Unsupported method");
    }
}
