package ua.task1.trainee.controller.commands.order;

import ua.task1.trainee.controller.commands.Command;
import ua.task1.trainee.controller.utils.WebUrl;
import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.service.IngredientService;
import ua.task1.trainee.service.SaladService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.view.View;
import ua.task1.trainee.model.entities.dishes.Salad;
import ua.task1.trainee.view.Page;
import ua.task1.trainee.controller.utils.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebUrl("/order/custom/salad")
public class OrderCustomSaladCommand implements Command {

    @Override
    public View executeGet(HttpServletRequest req, HttpServletResponse resp) {
        IngredientService service = ServiceFactory.getInstance().getIngredientService();
        req.setAttribute(RequestAttributes.IngredientAttributes.INGREDIENTS, service.getAllIngredients());
        req.setAttribute(RequestAttributes.ACTION, req.getServletPath());
        req.setAttribute(RequestAttributes.TITLE, "custom.title");
        return Page.CREATE_SALAD;
    }

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();

        try {
            Set<Integer> ingredientsId = convertToInteger(req.getParameterValues(RequestAttributes.ID));
            Set<Ingredient> saladIngredients = ingredientService.getIngredientsById(ingredientsId);
            processWeightFromRequest(saladIngredients, req);
            String saladName = req.getParameter(RequestAttributes.SaladAttributes.NAME);
            Salad salad = new Salad(saladName, saladIngredients);
            salad =  saladService.orderSalad(salad);
            req.setAttribute(RequestAttributes.SaladAttributes.SALAD, salad);
            return Page.SINGLE_SALAD;
        } catch (Exception e) {
            req.setAttribute(RequestAttributes.INVALID, e.getMessage());
            executeGet(req, resp);
            return Page.CREATE_SALAD;
        }
    }

    private static Set<Integer> convertToInteger(String[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Wrong id for ingredients");
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
