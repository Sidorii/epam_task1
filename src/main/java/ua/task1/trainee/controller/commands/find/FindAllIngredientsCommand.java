package ua.task1.trainee.controller.commands.find;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.service.IngredientService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.view.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static ua.task1.trainee.controller.utils.RequestAttributes.IngredientAttributes.INGREDIENTS;

@WebUrl("/ingredients")
public class FindAllIngredientsCommand implements Command {

    @Override
    public Page executeGet(HttpServletRequest req, HttpServletResponse resp) {
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        Set<Ingredient> ingredients = ingredientService.getAllIngredients();
        req.setAttribute(INGREDIENTS, ingredients);

        return Page.ALL_INGREDIENTS;
    }
}
