package ua.epam.trainee.controller.commands.find;

import ua.epam.trainee.controller.commands.Command;
import ua.epam.trainee.view.Page;
import ua.epam.trainee.controller.utils.WebUrl;
import ua.epam.trainee.model.entities.dishes.Salad;
import ua.epam.trainee.service.SaladService;
import ua.epam.trainee.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("salad")
public class FindSaladCommand implements Command {

    @Override
    public Page executeGet(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Salad salad = saladService.getSaladByName(req.getParameter("name"));
        req.setAttribute("salad", salad);
        return Page.SINGLE_SALAD;
    }
}
