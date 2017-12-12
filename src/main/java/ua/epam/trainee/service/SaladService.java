package ua.epam.trainee.service;

import ua.epam.trainee.model.entities.Ingredient;
import ua.epam.trainee.model.entities.dishes.Salad;

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

    void updateSalad(Salad salad);
}
