package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.service.IngredientService;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.view.View;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.view.Page;

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
        req.setAttribute("ingredients", service.getAllIngredients());
        return Page.CUSTOM_SALAD;
    }
    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();

        try {
            Set<Integer> ingredientsId = convertToInteger(req.getParameterValues("id"));
            Set<Ingredient> saladIngredients = ingredientService.getIngredientsById(ingredientsId);
            processWeightFromRequest(saladIngredients, req);
            String saladName = req.getParameter("name");
            Salad salad = new Salad(saladName, saladIngredients);
            salad =  saladService.orderSalad(salad);
            req.setAttribute("salad", salad);
            return Page.SINGLE_SALAD;
        } catch (Exception e) {
            req.setAttribute("exception", e.getMessage());
            executeGet(req, resp);
            return Page.CUSTOM_SALAD;
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
