package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.Ingredient;

import java.util.Set;

public interface IngredientDao {

    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(Ingredient ingredient);

    Ingredient getIngredientByName(Ingredient ingredient);

    Set<Ingredient> getIngredients(Set<Ingredient> ingredients);

    void removeIngredient(Ingredient ingredient);

    void updateIngredient(Ingredient ingredient);

    void batchUpdate(Set<Ingredient> ingredients);

    boolean contains(Integer id);
}
