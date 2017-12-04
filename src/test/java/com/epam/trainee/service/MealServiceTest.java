package com.epam.trainee.service;

import com.epam.trainee.model.entities.*;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.entities.dishes.SaladDish;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class MealServiceTest {

    private MealService mealService;
    private SaladService saladService;
    private List<Ingredient> saladIngredients;

    @Before
    public void setUp() {
        saladService = createMock(SaladService.class);
        mealService = new MealServiceImpl(saladService);

        Ingredient cucumber = MealIngredient.getIngredientBuilder(20)
                .setName("Cucumber")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Ingredient pork = MealIngredient.getIngredientBuilder(100)
                .setName("Pork")
                .setType(IngredientType.MEAT)
                .createIngredient();

        Ingredient tomato = MealIngredient.getIngredientBuilder(50)
                .setName("Tomato")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        saladIngredients = Arrays.asList(cucumber, tomato, pork);
    }

    @Test
    public void testOrderCustomSalad() {
        Salad salad = new Salad(saladIngredients);

        expect(saladService.orderSalad(saladIngredients))
                .andReturn(salad);
        replay(saladService);

        Meal expectedSalad = new SaladDish(salad, PackingType.PLATE);
        Meal resultSalad = mealService.orderSalad(saladIngredients);

        assertEquals(expectedSalad, resultSalad);
        verify(saladService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithEmptyIngredients() {
        saladIngredients = new ArrayList<>();
        mealService.orderSalad(saladIngredients);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithNullIngredients() {
        saladIngredients = null;
        mealService.orderSalad(saladIngredients);
    }

    @Test
    public void testOrderSaladByName() {
        Salad salad = new Salad(saladIngredients);

        expect(saladService.orderSalad(anyObject(String.class)))
                .andReturn(salad);
        replay(saladService);

        SaladDish expectedDish = new SaladDish(salad, PackingType.PLATE);
        Meal resultMeal = mealService.orderSalad("test salad");

        assertEquals(expectedDish, resultMeal);
        verify(saladService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladByNullName() {
        String saladName = null;
        mealService.orderSalad(saladName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladByEmptyName() {
        String saladName = "";
        mealService.orderSalad(saladName);
    }

    @Test
    public void testCreateRecipe() {
        Map<String, Integer> recipe = saladIngredients.stream()
                .collect(Collectors.groupingBy(Item::getName,
                        Collectors.reducing(0, e -> 1, Integer::sum)));

        saladService.createSaladRecipe(recipe);
        replay(saladService);

        mealService.createSaladRecipe(recipe);
        verify(saladService);
    }
}
