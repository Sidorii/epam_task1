package com.epam.trainee;


import com.epam.trainee.model.dao.DAOFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.entities.*;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.service.MealService;
import com.epam.trainee.service.SaladService;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

public class ArchitectureTest {

    private MealService mealService;
    private SaladService saladService;
    private IngredientDao ingredientDao;
    private SaladBuilder saladBuilder;
    private DAOFactory daoFactory;


    @Before
    public void setUp() {
        mealService = createMock(MealService.class);
        saladService = createMock(SaladService.class);
        saladBuilder = createMock(SaladBuilder.class);
        daoFactory = createMock(DAOFactory.class);
        ingredientDao = createMock(IngredientDao.class);
    }

    @Test
    public void testOrderSalad() {
        Ingredient ingredient = SaladIngredient.getIngredientBuilder(10)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Ingredient ingredient1 = SaladIngredient.getIngredientBuilder(20)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        ingredients.add(ingredient1);

        Salad salad = new Salad(ingredients, PackingType.PLATE);

        expect(mealService.orderSalad(new ArrayList<>(ingredients)))
                .andReturn(salad);
        expect(saladService.orderSalad(new ArrayList<>(ingredients)))
                .andReturn(salad);
        expect(daoFactory.getIngredientDao())
                .andReturn(ingredientDao);

        List<String> ingrNames = ingredients.stream()
                .map(Item::getName)
                .collect(Collectors.toList());

        expect(ingredientDao.getIngredientsByNames(ingrNames))
                .andReturn(ingredients);

        Iterator<Ingredient> it = ingredients.iterator();

        Salad orderedSalad = salad;

        Ingredient i1 = it.next();
        Ingredient i2 = it.next();

        expect(saladBuilder.addIngredient(i1))
                .andReturn(saladBuilder);

        expect(saladBuilder.addIngredient(i2))
                .andReturn(saladBuilder);
        expect(saladBuilder.buildSalad()).andReturn(orderedSalad);

        replay(mealService, saladService, saladBuilder, daoFactory, ingredientDao);

        Salad resultSalad = mealService.orderSalad(new ArrayList<>(ingredients));

        assertSame(orderedSalad, resultSalad);
    }
}
