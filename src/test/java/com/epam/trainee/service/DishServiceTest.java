package com.epam.trainee.service;

import com.epam.trainee.model.entities.*;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.entities.dishes.SaladDish;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class DishServiceTest {

    private DishService dishService;
    private SaladService saladService;
    private Set<Ingredient> saladIngredients;

    @Before
    public void setUp() {
        saladService = createMock(SaladService.class);
        dishService = new DishServiceImpl(saladService);

        Ingredient cucumber = IngredientImpl.getIngredientBuilder()
                .setWeight(20)
                .setName("Cucumber")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Ingredient pork = IngredientImpl.getIngredientBuilder()
                .setWeight(100)
                .setName("Pork")
                .setType(IngredientType.MEAT)
                .createIngredient();

        Ingredient tomato = IngredientImpl.getIngredientBuilder()
                .setWeight(50)
                .setName("Tomato")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        saladIngredients = new HashSet<>(Arrays.asList(cucumber, tomato, pork));
    }

    @Test
    public void testOrderCustomSalad() {
        Salad salad = new Salad(saladIngredients);

        expect(saladService.orderSalad(saladIngredients))
                .andReturn(salad);
        replay(saladService);

        Dish expectedSalad = new SaladDish(salad, PackingType.PLATE);
        Dish resultSalad = dishService.orderSalad(saladIngredients);

        assertEquals(expectedSalad, resultSalad);
        verify(saladService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithEmptyIngredients() {
        saladIngredients = new HashSet<>();
        dishService.orderSalad(saladIngredients);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithNullIngredients() {
        saladIngredients = null;
        dishService.orderSalad(saladIngredients);
    }

    @Test
    public void testOrderSaladByName() {
        Salad salad = new Salad(saladIngredients);

        expect(saladService.orderSalad(anyObject(String.class)))
                .andReturn(salad);
        replay(saladService);

        SaladDish expectedDish = new SaladDish(salad, PackingType.PLATE);
        Dish resultDish = dishService.orderSalad("test salad");

        assertEquals(expectedDish, resultDish);
        verify(saladService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladByNullName() {
        String saladName = null;
        dishService.orderSalad(saladName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladByEmptyName() {
        String saladName = "";
        dishService.orderSalad(saladName);
    }

    @Test
    public void testCreateRecipe() {
        saladService.createSaladRecipe("test", saladIngredients);
        replay(saladService);

        dishService.createSaladRecipe("test", saladIngredients);
        verify(saladService);
    }
}
