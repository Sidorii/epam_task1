package ua.task1.trainee.service;

import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.model.entities.dishes.Salad;

import java.util.Comparator;
import java.util.Set;

public interface SaladService {

    Salad orderSalad(Salad salad);

    Salad getSaladByName(String name);

    void createSaladRecipe(String name, Set<Ingredient> recipe);

    Set<Salad> getAllSalads();

    Set<Ingredient> sortIngredients(Salad salad, Comparator<Ingredient> comparator);

    Set<Ingredient> sortIngredients(Salad salad);

    void removeSalad(Integer id);
}
