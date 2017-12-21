package ua.task1.trainee.model.entities.dishes;

import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.model.entities.Meal;

import java.util.Set;

public interface Dish extends Meal {

    boolean isVegan();

    Set<Ingredient> getIngredients();
}
