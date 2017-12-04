package com.epam.trainee.model.entities;

public interface Meal extends Item {

    float getTotalCalories();

    double getTotalWeight();

    boolean isVegan();
}
