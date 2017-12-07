package com.epam.trainee.service;

import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientImpl;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.entities.dishes.Salad;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class SaladServiceTest {

    private SaladService saladService;
    private SaladDao saladDao;
    private IngredientDao ingredientDao;
    private Set<Ingredient> database;

    @Before
    public void setUp() {
        saladDao = createMock(SaladDao.class);
        ingredientDao = createMock(IngredientDao.class);

        saladService = new SaladServiceImpl(saladDao, ingredientDao);
        database = new HashSet<>();

        Ingredient cucumber = IngredientImpl.getIngredientBuilder()
                .setWeight(10000)
                .setName("Cucumber")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Ingredient pork = IngredientImpl.getIngredientBuilder()
                .setWeight(10000)
                .setName("Pork")
                .setType(IngredientType.MEAT)
                .createIngredient();

        Ingredient tomato = IngredientImpl.getIngredientBuilder()
                .setWeight(5000)
                .setName("Tomato")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();
        database.add(cucumber);
        database.add(pork);
        database.add(tomato);
    }

    @Test
    public void testOrderSalad() {
        /*init ingredients for new order*/
        Ingredient cucumber = IngredientImpl.getIngredientBuilder()
                .setName("Cucumber")
                .setWeight(20)
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

        /*creating new order*/
        Set<Ingredient> saladOrder = new HashSet<>();
        saladOrder.add(cucumber);
        saladOrder.add(pork);
        saladOrder.add(tomato);

        expect(ingredientDao.getIngredients(saladOrder))
                .andReturn(database);
        ingredientDao.batchUpdate(database);

        Set<Ingredient> saladIngredients = new HashSet<>();
        Iterator<Ingredient> dbIterator = database.iterator();
        Iterator<Ingredient> orderIterator = saladOrder.iterator();

        for (; dbIterator.hasNext() && orderIterator.hasNext(); ) {
            Ingredient requiredWeightAndType = orderIterator.next();
            Ingredient storedIngredient = dbIterator.next();

            Ingredient result = IngredientImpl.getIngredientBuilder()
                    .createFrom(storedIngredient)
                    .setWeight(requiredWeightAndType.getWeight())
                    .createIngredient();
            saladIngredients.add(result);
        }

        replay(ingredientDao);

        Salad expectedSalad = new Salad(saladIngredients);
        Salad realSalad = saladService.orderSalad(saladOrder);

        assertEquals(expectedSalad, realSalad);
        verify(ingredientDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithNullIngredients() {
        Set<Ingredient> ingredients = null;
        saladService.orderSalad(ingredients);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithEmptyIngredients() {
        Set<Ingredient> ingredients = new HashSet<>();
        saladService.orderSalad(ingredients);
    }

    @Test
    public void testOrderSaladByName() {
        Salad salad = new Salad(database);
        expect(saladDao.getSalad("test"))
                .andReturn(salad);
        replay(saladDao);

        Salad resultSalad = saladService.orderSalad("test");
        assertEquals(salad, resultSalad);
        verify(saladDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithNullName() {
        String name = null;
        saladService.orderSalad(name);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithEmptyName() {
        String name = "";
        saladService.orderSalad(name);
    }

    @Test
    public void testCreateSaladRecipe() {
        Salad salad = new Salad(database);
        salad.setName("test");
        saladDao.createSalad(salad);
        replay(saladDao);
        saladService.createSaladRecipe("test", database);
        verify(saladDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSaladRecipeWithNullIngredients() {
        Set<Ingredient> ingredients = null;
        saladService.createSaladRecipe("test", ingredients);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSaladRecipeWithEmptyIngredients() {
        Set<Ingredient> ingredients = new HashSet<>();
        saladService.createSaladRecipe("test", ingredients);
    }
}
