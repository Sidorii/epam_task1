package ua.epam.trainee.controller.commands.find;

import ua.epam.trainee.controller.commands.Command;
import ua.epam.trainee.controller.utils.WebUrl;
import ua.epam.trainee.model.entities.dishes.Salad;
import ua.epam.trainee.service.SaladService;
import ua.epam.trainee.service.ServiceFactory;
import ua.epam.trainee.view.Page;
import ua.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@WebUrl("salads")
public class FindAllSaladsCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Set<Salad> salads = saladService.getAllSalads();
        req.setAttribute("dishes", salads);
        return Page.ALL_SALADS;
    }
}
