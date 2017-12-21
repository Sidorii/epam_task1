package ua.task1.trainee.controller.commands.find;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.service.SaladService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.model.entities.dishes.Salad;
import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("salad")
public class FindSaladCommand implements Command {

    @Override
    public Page executeGet(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Salad salad = saladService.getSaladByName(req.getParameter(RequestAttributes.SaladAttributes.NAME));
        salad.setIngredients(saladService.sortIngredients(salad));
        req.setAttribute(RequestAttributes.SaladAttributes.SALAD, salad);
        return Page.SINGLE_SALAD;
    }
}
