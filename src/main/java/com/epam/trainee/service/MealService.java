package com.epam.trainee.service;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.dishes.Salad;

import java.util.List;
import java.util.Map;

public interface MealService {

    Salad orderSalad(List<Ingredient> ingredients);

    Salad createSaladRecipe(Map<String, Integer> ingredients);
}
