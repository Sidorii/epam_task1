package com.epam.trainee.service;

import com.epam.trainee.model.entities.Dish;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Packing;

import java.util.Set;

public interface DishService {

    Dish orderSalad(Set<Ingredient> ingredients);

    Dish orderSalad(String name);

    void createSaladRecipe(String saladName, Set<Ingredient> ingredients);
}
