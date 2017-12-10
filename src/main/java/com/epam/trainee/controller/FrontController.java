package com.epam.trainee.controller;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.entities.dishes.Dish;
import com.epam.trainee.service.DishService;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.impl.DishServiceImpl;
import com.epam.trainee.service.impl.SaladServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/")
public class FrontController extends HttpServlet {

    private SaladDao saladDao;
    private IngredientDao ingredientDao;
    private SaladService saladService;
    private DishService dishService;

    @Override
    public void init() throws ServletException {
        saladDao = DaoFactory.getInstance().getSaladDao();
        ingredientDao = DaoFactory.getInstance().getIngredientDao();
        saladService = new SaladServiceImpl(saladDao, ingredientDao);
        dishService = new DishServiceImpl(saladService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String saladName = req.getParameter("name");
        Set<Dish> dishes = dishService.getAllSalads();
        req.setAttribute("dishes", dishes);
        req.getRequestDispatcher("/WEB-INF/salads.jsp").forward(req, resp);
    }
}
