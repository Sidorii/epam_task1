package com.epam.trainee.model.entities;

public interface Meal extends Item {

    float getTotalCalories();

    float getTotalWeight();

    float getFoodWeight();

    boolean isVegan();
}
