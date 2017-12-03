package com.epam.trainee.service;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.dishes.Salad;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface SaladService {

    Salad orderSalad(List<Ingredient> ingredients);

    float calculateCalories(String saladName);

    double calculateWeight(String saladName);

    Set<Ingredient> sortIngredients(Salad salad, Comparator<Ingredient> comparator);

    Set<Ingredient> sortIngredients(Salad salad);
}
