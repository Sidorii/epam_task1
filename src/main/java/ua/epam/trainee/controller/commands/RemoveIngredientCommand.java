package ua.epam.trainee.controller.commands;

import ua.epam.trainee.controller.utils.WebUrl;
import ua.epam.trainee.service.IngredientService;
import ua.epam.trainee.service.ServiceFactory;
import ua.epam.trainee.view.Page;
import ua.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("/remove/ingredient")
public class RemoveIngredientCommand implements Command {

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        ingredientService.removeIngredient(id);
        req.setAttribute("ingredients", ingredientService.getAllIngredients());
        return Page.ALL_INGREDIENTS;
    }
}
