package com.epam.trainee;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientImpl;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.entities.dishes.Dish;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.impl.SaladServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class ArchitectureTest {

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

        Dish saladDish = new Salad(ingredients);

        expect(ingredientDao.getIngredients(ingredients))
                .andReturn(ingredients);
        Dish orderedSalad = saladDish;
        ingredientDao.batchUpdate(ingredients);

        replay(saladDao, daoFactory, ingredientDao);

        Dish resultSalad = saladService.orderSalad(ingredients);
        assertEquals(orderedSalad, resultSalad);
    }

    @After
    public void tearDown() {
        verify(saladDao, daoFactory, ingredientDao);
    }
}
