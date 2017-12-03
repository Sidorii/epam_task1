package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientDao {

    void addIngredient(Ingredient ingredient);

    Ingredient getIngredientByName(String name);

    Set<Ingredient> getIngredientsByNames(List<String> names);

    void removeIngredientByName(String name);

    void updateIngredient(Ingredient ingredient);

    void batchUpdate(Set<Ingredient> ingredients);
}
