package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.utils.WebUrl;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.view.Page;
import com.epam.trainee.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebUrl("/remove/salad")
public class RemoveSaladCommand implements Command {

    @Override
    public View executePost(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Integer id = Integer.valueOf(req.getParameter("id"));
        saladService.removeSalad(id);
        req.setAttribute("dishes", saladService.getAllSalads());
        return Page.ALL_SALADS;
    }
}
