package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.Ingredient;

import java.util.Set;

public interface IngredientDao extends GenericDao<Ingredient> {

    Ingredient getIngredientByName(String name);

    Set<Ingredient> getIngredients(Set<Ingredient> ingredients);

    void batchUpdate(Set<Ingredient> ingredients);
}
