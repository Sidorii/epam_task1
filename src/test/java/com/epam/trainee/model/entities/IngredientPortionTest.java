package com.epam.trainee.model.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientPortionTest {

    private Ingredient ingredient;

    @Before
    public void setUp() {
        ingredient = IngredientImpl.getIngredientBuilder()
                .setName("onion")
                .setFresh(true)
                .setPrice(50)
                .setType(IngredientType.VEGETABLE)
                .setDescription("just an onion")
                .setId(1)
                .setCalories(400)
                .setWeight(5000)
                .createIngredient();
    }


    @Test
    public void testConvert() {
        Ingredient converted = new IngredientPortion(ingredient, 50);

        assertEquals(ingredient.getName(), converted.getName());
        assertEquals(50, converted.getWeight(), 0.001);
        assertEquals(20, converted.getCalories(), 0.001);
        assertEquals(2.5, converted.getPrice(), 0.001);
        assertEquals(ingredient.getType(), converted.getType());
        assertEquals(ingredient.getId(), converted.getId());
        assertEquals(ingredient.getDescription(), converted.getDescription());
    }

    @Test
    public void testConvertToKg() {

        Ingredient converted = new IngredientPortion(ingredient, .5, 1);

        assertEquals(ingredient.getName(), converted.getName());
        assertEquals(.5, converted.getWeight(), 0.001);
        assertEquals(200, converted.getCalories(), 0.001);
        assertEquals(25, converted.getPrice(), 0.001);
        assertEquals(ingredient.getType(), converted.getType());
        assertEquals(ingredient.getId(), converted.getId());
        assertEquals(ingredient.getDescription(), converted.getDescription());
    }
}