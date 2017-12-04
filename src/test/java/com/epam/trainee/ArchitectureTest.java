package com.epam.trainee;


import com.epam.trainee.model.dao.DAOFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.entities.*;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.entities.dishes.SaladDish;
import com.epam.trainee.service.MealService;
import com.epam.trainee.service.SaladService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

public class ArchitectureTest {

    private MealService mealService;
    private SaladService saladService;
    private IngredientDao ingredientDao;
    private DAOFactory daoFactory;


    @Before
    public void setUp() {
        mealService = createMock(MealService.class);
        saladService = createMock(SaladService.class);
        daoFactory = createMock(DAOFactory.class);
        ingredientDao = createMock(IngredientDao.class);
    }

    @Test
    public void testOrderSalad() {
        Ingredient ingredient = MealIngredient.getIngredientBuilder(10)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Ingredient ingredient1 = MealIngredient.getIngredientBuilder(20)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        ingredients.add(ingredient1);

        Salad salad = new Salad(ingredients);
        Meal saladDish = new SaladDish(salad, PackingType.BOX);

        expect(mealService.orderSalad(new ArrayList<>(ingredients)))
                .andReturn(saladDish);
        expect(saladService.orderSalad(new ArrayList<>(ingredients)))
                .andReturn(salad);
        expect(daoFactory.getIngredientDao())
                .andReturn(ingredientDao);

        List<String> ingrNames = ingredients.stream()
                .map(Item::getName)
                .collect(Collectors.toList());

        expect(ingredientDao.getIngredientsByNames(ingrNames))
                .andReturn(new HashSet<>(ingredients));

        Iterator<Ingredient> it = ingredients.iterator();

        Meal orderedSalad = saladDish;

        replay(mealService, saladService,  daoFactory, ingredientDao);

        Meal resultSalad = mealService.orderSalad(new ArrayList<>(ingredients));

        assertSame(orderedSalad, resultSalad);
    }

    @After
    public void tearDown() {
        verify(mealService, saladService, daoFactory, ingredientDao);
    }
}
