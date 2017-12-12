package ua.epam.trainee.model.dao;

import ua.epam.trainee.model.entities.Ingredient;

import java.util.Set;

public interface IngredientDao extends GenericDao<Ingredient> {

    Ingredient getIngredientByName(String name);

    Set<Ingredient> getIngredients(Set<Ingredient> ingredients);

    void mergeIngredientsWeight(Set<Ingredient> ingredients);

    Set<Ingredient> findIngredientsById(Set<Integer> idSet);
}
