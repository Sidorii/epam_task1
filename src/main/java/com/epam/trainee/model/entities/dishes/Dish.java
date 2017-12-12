package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Meal;

import java.util.Set;

public interface Dish extends Meal {

    boolean isVegan();

    Set<Ingredient> getIngredients();
}
