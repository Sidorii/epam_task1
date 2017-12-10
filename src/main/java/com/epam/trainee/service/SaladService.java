package com.epam.trainee.service;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.dishes.Salad;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SaladService {

    Salad orderSalad(Set<Ingredient> ingredients);

    Salad orderSalad(String name);

    void createSaladRecipe(String name, Set<Ingredient> recipe);

    Set<Salad> getAllSalads();

    Set<Ingredient> sortIngredients(Salad salad, Comparator<Ingredient> comparator);

    Set<Ingredient> sortIngredients(Salad salad);
}
