package com.epam.trainee.controller.commands.create;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientImpl;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.service.IngredientService;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.view.Page;
import com.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("storekeeper/create/ingredient")
public class CreateIngredientCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("types", IngredientType.values());
        return Page.CREATE_INGREDIENT;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        Ingredient ingredient;
        try {
             ingredient = IngredientImpl.getIngredientBuilder()
                    .setName(req.getParameter("name"))
                    .setWeight(Double.valueOf(req.getParameter("weight")))
                    .setCalories(Float.valueOf(req.getParameter("calories")))
                    .setPrice(Float.valueOf(req.getParameter("price")))
                    .setFresh(Boolean.valueOf(req.getParameter("fresh")))
                    .setDescription(req.getParameter("description"))
                    .setType(IngredientType.valueOf(req.getParameter("type")))
                    .createIngredient();
        } catch (NumberFormatException e) {
            req.setAttribute("exception", e.getMessage());
            return Page.CREATE_INGREDIENT;
        }

        ingredient = ingredientService.createIngredient(ingredient);
        req.setAttribute("ingredient", ingredient);
        return Page.SINGLE_INGREDIENT;
    }
}
