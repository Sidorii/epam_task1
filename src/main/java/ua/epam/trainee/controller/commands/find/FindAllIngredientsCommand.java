package ua.epam.trainee.controller.commands.find;

import ua.epam.trainee.controller.commands.Command;
import ua.epam.trainee.view.Page;
import ua.epam.trainee.controller.utils.WebUrl;
import ua.epam.trainee.model.entities.Ingredient;
import ua.epam.trainee.service.IngredientService;
import ua.epam.trainee.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@WebUrl("/ingredients")
public class FindAllIngredientsCommand implements Command {

    @Override
    public Page executeGet(HttpServletRequest req, HttpServletResponse resp) {
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        Set<Ingredient> ingredients = ingredientService.getAllIngredients();
        req.setAttribute("ingredients", ingredients);
        return Page.ALL_INGREDIENTS;
    }
}
