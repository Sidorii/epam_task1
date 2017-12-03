package com.epam.trainee.model.entities;

import com.epam.trainee.model.entities.dishes.Salad;

public interface SaladBuilder {

    SaladCookBook getCookBook();

    Salad buildSalad();

    SaladBuilder addIngredient(Ingredient ingredient);
}
