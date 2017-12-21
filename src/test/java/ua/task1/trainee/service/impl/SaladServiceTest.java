package ua.task1.trainee.service.impl;

import ua.task1.trainee.model.dao.SaladDao;
import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.model.entities.IngredientType;
import ua.task1.trainee.model.exceptions.MissingItemException;
import ua.task1.trainee.model.dao.IngredientDao;
import ua.task1.trainee.model.entities.IngredientImpl;
import ua.task1.trainee.model.entities.dishes.Salad;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class SaladServiceTest {

    private SaladServiceImpl saladService;
    private SaladDao saladDao;
    private IngredientDao ingredientDao;
    private Set<Ingredient> database;

    @Before
    public void setUp() {
        saladDao = createMock(SaladDao.class);
        ingredientDao = createMock(IngredientDao.class);

        saladService = SaladServiceImpl.getInstance();
        saladService.saladDao = saladDao;
        saladService.ingredientDao = ingredientDao;
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
        ingredientDao.mergeIngredientsWeight(database);

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
        Salad realSalad = saladService.orderSalad(expectedSalad);

        assertEquals(expectedSalad, realSalad);
        verify(ingredientDao);
    }

    @Test(expected = MissingItemException.class)
    public void testOrderSaladWithNullSalad() {
        saladService.orderSalad(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithEmptyIngredients() {
        Set<Ingredient> ingredients = new HashSet<>();
        saladService.orderSalad(new Salad(ingredients));
    }

    @Test
    public void testOrderSaladByName() {
        Salad salad = new Salad(database);
        expect(saladDao.getSaladByName("test"))
                .andReturn(salad);
        replay(saladDao);

        Salad resultSalad = saladService.getSaladByName("test");
        assertEquals(salad, resultSalad);
        verify(saladDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithNullName() {
        String name = null;
        saladService.getSaladByName(name);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderSaladWithEmptyName() {
        String name = "";
        saladService.getSaladByName(name);
    }

    @Test
    public void testCreateSaladRecipe() {
        Salad salad = new Salad(database);
        salad.setName("test");
        expect(saladDao.addEntity(salad))
        .andReturn(salad);
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
