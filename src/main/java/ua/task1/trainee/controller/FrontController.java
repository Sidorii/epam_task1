package ua.task1.trainee.controller;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.commands.resolvers.CommandResolver;
import ua.task1.trainee.model.exceptions.DuplicatedEntryException;
import ua.task1.trainee.model.exceptions.MissingEntityException;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;

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
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        View view;

        try {
            Command command = CommandResolver.getInstance().resolveCommand(req);
            view = command.execute(req, resp);
        } catch (MissingEntityException e) {
            e.printStackTrace();
            view = processErrorPage(req, e);
        } catch (IllegalArgumentException | IllegalStateException | UnsupportedOperationException e) {
            e.printStackTrace();
            view = Page.NOT_FOUND;
        } catch (DuplicatedEntryException e) {
            req.setAttribute("entry", e.getEntry());
            req.setAttribute("message", e.getMessage());
            view = Page.DUPLICATED_ENTRY;
        }
        req.getSession().setAttribute("page", view);
        req.getRequestDispatcher(view.getView()).forward(req, resp);
    }

    private Page processErrorPage(HttpServletRequest req, MissingEntityException e) {
        req.setAttribute("entity", e.getEntity());
        req.setAttribute("err_message", e.getMessage());
        return Page.MISSED_ENTITY;
    }
}
