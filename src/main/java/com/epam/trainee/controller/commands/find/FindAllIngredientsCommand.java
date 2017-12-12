package com.epam.trainee.controller.commands;

import com.epam.trainee.view.Page;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.service.IngredientService;
import com.epam.trainee.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@WebUrl("/ingredients")
public class IngredientsCommand implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        Set<Ingredient> ingredients = ingredientService.getAllIngredients();
        req.setAttribute("ingredients", ingredients);
        return Page.ALL_INGREDIENTS;
    }
}
