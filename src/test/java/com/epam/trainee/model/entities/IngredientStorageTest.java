package com.epam.trainee.model.entities;

import com.epam.trainee.model.exceptions.MissingItemException;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class IngredientStorageTest {

    private IngredientStorage storage;
    private Set<Ingredient> ingredients;

    @Before
    public void setUp() {
        ingredients = new HashSet<>();

        Ingredient cucumber = IngredientImpl.getIngredientBuilder()
                .setWeight(1000)
                .setName("Cucumber")
                .setCalories(1000)
                .setFresh(true)
                .setPrice(50)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Ingredient pork = IngredientImpl.getIngredientBuilder()
                .setWeight(100)
                .setName("Pork")
                .setCalories(1000)
                .setFresh(true)
                .setPrice(50)
                .setType(IngredientType.MEAT)
                .createIngredient();

        Ingredient tomato = IngredientImpl.getIngredientBuilder()
                .setWeight(50)
                .setName("Tomato")
                .setCalories(1000)
                .setFresh(true)
                .setPrice(50)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();
        ingredients.add(cucumber);
        ingredients.add(pork);
        ingredients.add(tomato);

        storage = new IngredientStorage(ingredients);
    }

    @Test
    public void testPullIngredient() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setWeight(10)
                .setName("Cucumber")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        Ingredient result = storage.produceIngredient(ingredient);

        assertEquals(ingredient.getWeight(), result.getWeight(), 0.00001);
        assertEquals("Cucumber", result.getName());
        assertEquals(10, result.getCalories(), 0.001);
        assertEquals(true, result.isFresh());
        assertEquals(0.5, result.getPrice(), 0.001);
        assertEquals(IngredientType.VEGETABLE, result.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPullNullArg() {
        storage.produceIngredient(null);
    }

    @Test(expected = MissingItemException.class)
    public void testPullMoreThanStorageHas() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setWeight(100500)
                .setName("Cucumber")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();
        storage.produceIngredient(ingredient);
    }

    @Test
    public void testPullIngredientsSet() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setWeight(10)
                .setName("Cucumber")
                .setType(IngredientType.VEGETABLE)
                .createIngredient();
        Ingredient ingredient2 = IngredientImpl.getIngredientBuilder()
                .setWeight(15)
                .setName("Pork")
                .setType(IngredientType.MEAT)
                .createIngredient();
        Set<Ingredient> order = new HashSet<>();
        order.add(ingredient);
        order.add(ingredient2);

        TreeSet<Ingredient> result = new TreeSet<>(Comparator.comparing(Item::getName));
        result.addAll(storage.produceIngredients(order));
        Ingredient result1 = result.first();

        assertEquals("Cucumber", result1.getName());
        assertEquals(ingredient.getWeight(), result1.getWeight(), .00001);
        assertEquals(IngredientType.VEGETABLE, result1.getType());
        assertEquals(10, result1.getCalories(), 0.0001);
        assertEquals(.5, result1.getPrice(), 0.01);
        assertEquals(true, result1.isFresh());

        Ingredient result2 = result.last();
        assertEquals("Pork", result2.getName());
        assertEquals(ingredient2.getWeight(), result2.getWeight(), .00001);
        assertEquals(IngredientType.MEAT, result2.getType());
        assertEquals(15, result2.getCalories(), 0.0001);
        assertEquals(.75, result2.getPrice(), 0.01);
        assertEquals(true, result2.isFresh());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPullWithNullArg() {
        storage.produceIngredients(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPullWithEmptyArg() {
        storage.produceIngredients(new HashSet<>());
    }

    @Test(expected = MissingItemException.class)
    public void testPullMoreIngredientsThanStorageHas() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setWeight(100500)
                .setName("Pork")
                .setType(IngredientType.MEAT)
                .createIngredient();
        Set<Ingredient> order = new HashSet<>();
        order.add(ingredient);
        storage.produceIngredients(order);
    }
}