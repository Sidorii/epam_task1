package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.entities.Meal;

public interface Dish extends Meal {

    double getTotalWeight();

    boolean isVegan();
}
