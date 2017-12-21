package ua.task1.trainee.controller.commands.find;

import ua.task1.trainee.service.SaladService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.model.entities.dishes.Salad;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;
import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@WebUrl("salads")
public class FindAllSaladsCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Set<Salad> salads = saladService.getAllSalads();
        req.setAttribute(RequestAttributes.SaladAttributes.DISHES, salads);
        return Page.ALL_SALADS;
    }
}
