package com.epam.trainee.model.entities;

public interface SaladBuilder {

    SaladCookBook getCookBook();

    Meal buildSalad();

    SaladBuilder addIngredient(Ingredient ingredient);
}
