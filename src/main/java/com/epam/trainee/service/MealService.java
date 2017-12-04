package com.epam.trainee.service;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Meal;
import com.epam.trainee.model.entities.dishes.Salad;

import java.util.List;
import java.util.Map;

public interface MealService {

    Meal orderSalad(List<Ingredient> ingredients);

    Meal orderSalad(String name);

    void createSaladRecipe(Map<String, Integer> ingredients);
}
