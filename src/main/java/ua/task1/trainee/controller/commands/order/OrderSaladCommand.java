package ua.task1.trainee.controller.commands.order;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.service.SaladService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.model.entities.dishes.Salad;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;

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
