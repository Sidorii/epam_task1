package com.epam.trainee;


import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.entities.*;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.entities.dishes.SaladDish;
import com.epam.trainee.service.DishService;
import com.epam.trainee.service.SaladService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

public class ArchitectureTest {

    private DishService dishService;
    private SaladService saladService;
    private IngredientDao ingredientDao;
    private DaoFactory daoFactory;


    @Before
    public void setUp() {
        dishService = createMock(DishService.class);
        saladService = createMock(SaladService.class);
        daoFactory = createMock(DaoFactory.class);
        ingredientDao = createMock(IngredientDao.class);
    }

    @Test
    public void testOrderSalad() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setName("Cucumber")
                .setWeight(10)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Ingredient ingredient1 = IngredientImpl.getIngredientBuilder()
                .setName("Potato")
                .setWeight(20)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        ingredients.add(ingredient1);

        Salad salad = new Salad(ingredients);
        Dish saladDish = new SaladDish(salad, PackingType.BOX);

        expect(dishService.orderSalad(ingredients))
                .andReturn(saladDish);
        expect(saladService.orderSalad(ingredients))
                .andReturn(salad);
        expect(daoFactory.getIngredientDao())
                .andReturn(ingredientDao);


        expect(ingredientDao.getIngredients(ingredients))
                .andReturn(new HashSet<>(ingredients));

        Dish orderedSalad = saladDish;

        replay(dishService, saladService,  daoFactory, ingredientDao);

        Dish resultSalad = dishService.orderSalad(ingredients);

        assertSame(orderedSalad, resultSalad);
    }

    @After
    public void tearDown() {
        verify(dishService, saladService, daoFactory, ingredientDao);
    }
}
