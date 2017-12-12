package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.commands.Command;
import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.view.Page;
import com.epam.trainee.model.entities.dishes.Salad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("salad")
public class FindSaladCommand implements Command {

    @Override
    public Page executeGet(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Salad salad = saladService.getSaladByName(req.getParameter("name"));
        req.setAttribute("salad", salad);
        return Page.SINGLE_SALAD;
    }
}
