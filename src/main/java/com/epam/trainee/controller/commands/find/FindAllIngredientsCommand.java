package com.epam.trainee.controller.commands.find;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.service.IngredientService;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.view.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static com.epam.trainee.controller.utils.RequestAttributes.IngredientAttributes.INGREDIENTS;

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
