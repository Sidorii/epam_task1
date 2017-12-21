package com.epam.trainee.controller.commands.create;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.utils.RequestAttributes;
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

import static com.epam.trainee.controller.utils.RequestAttributes.*;

@WebUrl("storekeeper/create/ingredient")
public class CreateIngredientCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(TYPES, IngredientType.values());
        return Page.CREATE_INGREDIENT;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        Ingredient ingredient;
        try {
             ingredient = IngredientImpl.getIngredientBuilder()
                    .setName(req.getParameter(IngredientAttributes.NAME))
                    .setWeight(Double.valueOf(req.getParameter(IngredientAttributes.WEIGHT)))
                    .setCalories(Float.valueOf(req.getParameter(IngredientAttributes.CALORIES)))
                    .setPrice(Float.valueOf(req.getParameter(IngredientAttributes.PRICE)))
                    .setFresh(Boolean.valueOf(req.getParameter(IngredientAttributes.IS_FRESH)))
                    .setDescription(req.getParameter(IngredientAttributes.DESC))
                    .setType(IngredientType.valueOf(IngredientAttributes.TYPE))
                    .createIngredient();
        } catch (NumberFormatException e) {
            req.setAttribute(INVALID, e.getMessage());
            return Page.CREATE_INGREDIENT;
        }

        ingredient = ingredientService.createIngredient(ingredient);
        req.setAttribute(IngredientAttributes.INGREDIENT, ingredient);
        return Page.SINGLE_INGREDIENT;
    }
}
