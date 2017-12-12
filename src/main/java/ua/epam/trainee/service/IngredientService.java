package ua.epam.trainee.service;

import ua.epam.trainee.model.entities.Ingredient;

import java.util.Set;

public interface IngredientService{

    Ingredient createIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);

    Ingredient getIngredientByName(String name);

    Set<Ingredient> getIngredientsById(Set<Integer> idSet);

    Set<Ingredient> getAllIngredients();

    void updateIngredient(Ingredient ingredient);

    void removeIngredient(Integer id);
}
