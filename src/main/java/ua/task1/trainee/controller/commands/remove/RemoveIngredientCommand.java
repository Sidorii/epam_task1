package ua.task1.trainee.controller.commands.remove;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.service.IngredientService;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;
import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("storekeeper/remove/ingredient")
public class RemoveIngredientCommand implements Command {

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter(RequestAttributes.ID));
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        ingredientService.removeIngredient(id);
        req.setAttribute(RequestAttributes.IngredientAttributes.INGREDIENTS, ingredientService.getAllIngredients());
        return Page.ALL_INGREDIENTS;
    }
}
