package com.epam.trainee.controller.commands.remove;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.service.IngredientService;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.view.Page;
import com.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("/remove/ingredient")
public class RemoveIngredientCommand implements Command {

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        IngredientService ingredientService = ServiceFactory.getInstance().getIngredientService();
        ingredientService.removeIngredient(id);
        req.setAttribute("ingredients", ingredientService.getAllIngredients());
        return Page.ALL_INGREDIENTS;
    }
}
