package ua.task1.trainee.controller.commands.create;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.RequestAttributes;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.model.entities.IngredientImpl;
import ua.task1.trainee.model.entities.IngredientType;
import ua.task1.trainee.service.IngredientService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("storekeeper/create/ingredient")
public class CreateIngredientCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(RequestAttributes.TYPES, IngredientType.values());
        return Page.CREATE_INGREDIENT;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        Ingredient ingredient;
        try {
             ingredient = IngredientImpl.getIngredientBuilder()
                    .setName(req.getParameter(RequestAttributes.IngredientAttributes.NAME))
                    .setWeight(Double.valueOf(req.getParameter(RequestAttributes.IngredientAttributes.WEIGHT)))
                    .setCalories(Float.valueOf(req.getParameter(RequestAttributes.IngredientAttributes.CALORIES)))
                    .setPrice(Float.valueOf(req.getParameter(RequestAttributes.IngredientAttributes.PRICE)))
                    .setFresh(Boolean.valueOf(req.getParameter(RequestAttributes.IngredientAttributes.IS_FRESH)))
                    .setDescription(req.getParameter(RequestAttributes.IngredientAttributes.DESC))
                    .setType(IngredientType.valueOf(RequestAttributes.IngredientAttributes.TYPE))
                    .createIngredient();
        } catch (NumberFormatException e) {
            req.setAttribute(RequestAttributes.INVALID, e.getMessage());
            return Page.CREATE_INGREDIENT;
        }

        ingredient = ingredientService.createIngredient(ingredient);
        req.setAttribute(RequestAttributes.IngredientAttributes.INGREDIENT, ingredient);
        return Page.SINGLE_INGREDIENT;
    }
}
