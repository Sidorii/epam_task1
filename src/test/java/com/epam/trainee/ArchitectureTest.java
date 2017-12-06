package com.epam.trainee;


import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.entities.*;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.entities.dishes.SaladDish;
import com.epam.trainee.service.DishService;
import com.epam.trainee.service.DishServiceImpl;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.SaladServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ArchitectureTest {

    private DishService dishService;
    private SaladService saladService;
    private IngredientDao ingredientDao;
    private DaoFactory daoFactory;
    private SaladDao saladDao;


    @Before
    public void setUp() {
        daoFactory = createMock(DaoFactory.class);
        ingredientDao = createMock(IngredientDao.class);
        saladDao = createMock(SaladDao.class);
        saladService = new SaladServiceImpl(saladDao, ingredientDao);
        dishService = new DishServiceImpl(saladService);
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

        DaoFactory.setInstance(daoFactory);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        ingredients.add(ingredient1);

        Salad salad = new Salad(ingredients);
        Dish saladDish = new SaladDish(salad, PackingType.PLATE);

        expect(ingredientDao.getIngredients(ingredients))
                .andReturn(ingredients);
        Dish orderedSalad = saladDish;
        ingredientDao.batchUpdate(ingredients);

        replay(saladDao,daoFactory, ingredientDao);

        Dish resultSalad = dishService.orderSalad(ingredients);
        assertEquals(orderedSalad, resultSalad);
    }

    @After
    public void tearDown() {
        verify(saladDao,daoFactory, ingredientDao);
    }
}
