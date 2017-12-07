package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class SaladTest {

    private Salad salad;
    private Set<Ingredient> ingredients;
    private Ingredient cucumber;
    private Ingredient pork;
    private Ingredient tomato;

    @Before
    public void setUp() {
        ingredients = new HashSet<>();
        cucumber = createMock(Ingredient.class);
        pork = createMock(Ingredient.class);
        tomato = createMock(Ingredient.class);

        ingredients.add(cucumber);
        ingredients.add(pork);
        ingredients.add(tomato);
        salad = new Salad("test", ingredients);
    }

    @Test
    public void testGetCalories() {
        expect(cucumber.getCalories())
                .andReturn(100f);
        expect(pork.getCalories())
                .andReturn(500f);
        expect(tomato.getCalories())
                .andReturn(130f);
        replay(cucumber, pork, tomato);
        assertEquals(730f, salad.getCalories(), .01);
        verify(cucumber, pork, tomato);
    }

    @Test
    public void testGetWeight() {
        expect(cucumber.getWeight())
                .andReturn(100d);
        expect(pork.getWeight())
                .andReturn(500d);
        expect(tomato.getWeight())
                .andReturn(130d);

        replay(cucumber, pork, tomato);
        assertEquals(730d, salad.getWeight(), .01);
        verify(cucumber, pork, tomato);
    }

    @Test
    public void testGetName() {
        assertEquals("test", salad.getName());
    }

    @Test
    public void testName() {
        salad.setName("new name");
        assertEquals("new name", salad.getName());
    }

    @Test
    public void testPrice() {
        expect(cucumber.getPrice())
                .andReturn(100f);
        expect(pork.getPrice())
                .andReturn(500f);
        expect(tomato.getPrice())
                .andReturn(130f);

        replay(cucumber, pork, tomato);
        assertEquals(730f, salad.getPrice(), .01);
        verify(cucumber, pork, tomato);
    }

    @Test
    public void testDescription() {
        assertNull(salad.getDescription());
        salad.setDescription("test");
        assertEquals("test", salad.getDescription());
    }

    @Test
    public void testIsVegan() {
        expect(cucumber.getType())
                .andReturn(IngredientType.VEGETABLE)
                .anyTimes();
        expect(pork.getType())
                .andReturn(IngredientType.MEAT)
                .anyTimes();
        expect(tomato.getType())
                .andReturn(IngredientType.VEGETABLE)
                .anyTimes();
        replay(cucumber, pork, tomato);

        assertFalse(salad.isVegan());
        ingredients.remove(pork);
        assertTrue(salad.isVegan());
    }

    @Test
    public void acceptVisitor() {
        //TODO: implementation required
    }
}