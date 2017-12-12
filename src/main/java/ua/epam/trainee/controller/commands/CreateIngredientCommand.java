package ua.epam.trainee.controller.commands;

import ua.epam.trainee.controller.utils.WebUrl;
import ua.epam.trainee.model.entities.Ingredient;
import ua.epam.trainee.model.entities.IngredientImpl;
import ua.epam.trainee.model.entities.IngredientType;
import ua.epam.trainee.service.IngredientService;
import ua.epam.trainee.service.ServiceFactory;
import ua.epam.trainee.view.Page;
import ua.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("/create/ingredient")
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

        ingredientService.createIngredient(ingredient);
        req.setAttribute("ingredient", ingredient);
        return Page.SINGLE_INGREDIENT;
    }
}
