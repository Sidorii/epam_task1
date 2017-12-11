package com.epam.trainee.service;

import com.epam.trainee.model.entities.Ingredient;

import java.util.Set;

public interface IngredientService{

    Ingredient createIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);

    Ingredient getIngredientByName(String name);

    Set<Ingredient> getAllIngredients();

    void updateIngredient(Ingredient ingredient);

    void updateIngredients(Set<Ingredient> ingredients);
}
