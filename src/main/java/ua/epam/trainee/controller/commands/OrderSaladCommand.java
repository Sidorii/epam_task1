package ua.epam.trainee.controller.commands;

import ua.epam.trainee.controller.utils.WebUrl;
import ua.epam.trainee.model.entities.dishes.Salad;
import ua.epam.trainee.service.IngredientService;
import ua.epam.trainee.service.SaladService;
import ua.epam.trainee.service.ServiceFactory;
import ua.epam.trainee.view.Page;
import ua.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("/order/salad")
public class OrderSaladCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Salad salad = saladService.getSaladByName(req.getParameter("name"));
        salad = saladService.orderSalad(salad);
        req.setAttribute("salad", salad);
        return Page.SINGLE_SALAD;
    }
}
