package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.Page;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebUrl("salad")
public class FindSalad implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Salad salad = saladService.orderSalad(req.getParameter("name"));
        req.setAttribute("salad", salad);
        return Page.SINGLE_SALAD;
    }
}
