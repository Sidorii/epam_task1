package com.epam.trainee.service;

import com.epam.trainee.model.entities.Dish;
import com.epam.trainee.model.entities.Ingredient;

import java.util.Set;

public interface DishService {

    Dish orderSalad(Set<Ingredient> ingredients);

    Dish orderSalad(String name);

    void createSaladRecipe(Set<Ingredient> ingredients);
}
