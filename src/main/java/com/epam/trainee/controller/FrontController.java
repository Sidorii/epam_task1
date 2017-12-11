package com.epam.trainee.controller;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.commands.resolver.CommandResolver;
import com.epam.trainee.model.exceptions.MissingEntityException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page;
        try {
            Command command = CommandResolver.getInstance().resolveCommand(req);
            page = command.execute(req, resp);
        } catch (MissingEntityException e) {
            e.printStackTrace();
            page = processErrorPage(req, e);
        }
        req.getRequestDispatcher(page.getView()).forward(req, resp);
    }

    private Page processErrorPage(HttpServletRequest req, MissingEntityException e) {
        req.setAttribute("entity", e.getEntity());
        req.setAttribute("err_message", e.getMessage());
        return Page.MISSED_ENTITY;
    }
}
