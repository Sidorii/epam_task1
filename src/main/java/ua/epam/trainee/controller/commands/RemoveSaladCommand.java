package ua.epam.trainee.controller.commands;

import ua.epam.trainee.controller.utils.WebUrl;
import ua.epam.trainee.service.SaladService;
import ua.epam.trainee.service.ServiceFactory;
import ua.epam.trainee.view.Page;
import ua.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebUrl("/remove/salad")
public class RemoveSaladCommand implements Command {

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Integer id = Integer.valueOf(req.getParameter("id"));
        saladService.removeSalad(id);
        req.setAttribute("dishes", saladService.getAllSalads());
        return Page.ALL_SALADS;
    }
}
