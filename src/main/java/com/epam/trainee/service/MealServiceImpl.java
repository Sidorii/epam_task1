package com.epam.trainee.service;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Meal;
import com.epam.trainee.model.entities.Packing;
import com.epam.trainee.model.entities.PackingType;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.entities.dishes.SaladDish;

import java.util.*;

public class MealServiceImpl implements MealService {

    private SaladService saladService;

    public MealServiceImpl(SaladService saladService) {
        this.saladService = saladService;
    }

    @Override
    public Meal orderSalad(List<Ingredient> ingredients) {
        throwIfInvalidIngredients(ingredients);
        return createSaladDish(ingredients);
    }

    private void throwIfInvalidIngredients(Collection ingredients) {
        if ((ingredients == null || ingredients.size() < 1)) {
            throw new IllegalArgumentException("Ingredients is null or has" +
                    " invalid size: " + ingredients);
        }
    }

    private SaladDish createSaladDish(List<Ingredient> ingredients) {
        Salad salad = saladService.orderSalad(ingredients);
        Packing packing = PackingType.PLATE;
        return newSaladDish(salad, packing);
    }

    private SaladDish newSaladDish(Salad salad, Packing packing) {
        return new SaladDish(salad, packing);
    }

    @Override
    public Meal orderSalad(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Invalid salad name: " + '\'' + name + '\'');
        }
        return newSaladDish(saladService.orderSalad(name), PackingType.PLATE);
    }


    @Override
    public void createSaladRecipe(Map<String, Integer> ingredients) {
        if (ingredients != null) {
            throwIfInvalidIngredients(ingredients.entrySet());
            saladService.createSaladRecipe(ingredients);
        }
    }
}
