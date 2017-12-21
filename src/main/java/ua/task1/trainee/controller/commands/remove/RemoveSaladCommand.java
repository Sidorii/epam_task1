package ua.task1.trainee.controller.commands.remove;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.service.SaladService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;
import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("chef/remove/salad")
public class RemoveSaladCommand implements Command {

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Integer id = Integer.valueOf(req.getParameter(RequestAttributes.ID));
        saladService.removeSalad(id);
        req.setAttribute(RequestAttributes.SaladAttributes.DISHES, saladService.getAllSalads());
        return Page.ALL_SALADS;
    }
}
