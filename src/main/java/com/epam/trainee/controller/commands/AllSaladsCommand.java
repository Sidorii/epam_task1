package com.epam.trainee.controller.commands;

import com.epam.trainee.controller.Page;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@WebUrl("salads")
public class AllSaladsCommand implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        SaladService saladService = ServiceFactory.getInstance().getSaladService();
        Set<Salad> salads = saladService.getAllSalads();
        req.setAttribute("dishes", salads);
        return Page.ALL_SALADS;
    }
}
