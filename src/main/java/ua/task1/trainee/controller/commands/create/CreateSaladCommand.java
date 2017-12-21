package ua.task1.trainee.controller.commands.create;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.RequestAttributes;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.model.entities.dishes.Salad;
import ua.task1.trainee.service.IngredientService;
import ua.task1.trainee.service.SaladService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ua.task1.trainee.controller.utils.RequestAttributes.*;
import static ua.task1.trainee.controller.utils.RequestAttributes.SaladAttributes.SALAD;

@WebUrl("chef/create/salad")
public class CreateSaladCommand implements Command {

    private IngredientService ingredientService;
    private SaladService saladService;

    public CreateSaladCommand() {
        saladService = ServiceFactory.getInstance().getSaladService();
        ingredientService = ServiceFactory.getInstance().getIngredientService();
    }

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(RequestAttributes.IngredientAttributes.INGREDIENTS, ingredientService.getAllIngredients());
        req.setAttribute(ACTION, req.getServletPath());
        req.setAttribute(TITLE, "new.title");
        return Page.CREATE_SALAD;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Set<Integer> ingredientsId = convertToInteger(req.getParameterValues(ID));
            Set<Ingredient> saladIngredients = ingredientService.getIngredientsById(ingredientsId);
            processWeightFromRequest(saladIngredients, req);
            String saladName = req.getParameter(RequestAttributes.SaladAttributes.NAME);
            saladService.createSaladRecipe(saladName, saladIngredients);
            Salad salad = saladService.getSaladByName(saladName);
            req.setAttribute(SALAD, salad);
            return Page.SINGLE_SALAD;
        } catch (Exception e) {
            req.setAttribute(INVALID, e.getMessage());
            executeGet(req, resp);
            return Page.CREATE_SALAD;
        }
    }

    private static Set<Integer> convertToInteger(String[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Ingredients ids for recipe can't be null");
        }
        return Stream.of(array)
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
    }

    private void processWeightFromRequest(Set<Ingredient> ingredients, HttpServletRequest req) {
        ingredients.forEach(ingredient -> {
            double weight = Double.parseDouble(req.getParameter(ingredient.getId() + "_weight"));
            ingredient.setWeight(weight);
        });
    }
}
