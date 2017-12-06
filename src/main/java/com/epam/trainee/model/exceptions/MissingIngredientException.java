package com.epam.trainee.model.exceptions;

import com.epam.trainee.model.entities.Ingredient;

public class MissingIngredientException extends RuntimeException{

    private Ingredient ingredient;

    public MissingIngredientException(Ingredient ingredient) {
        super("Required ingredient is missed: " + ingredient.getName());
        this.ingredient = ingredient;
    }

    public MissingIngredientException(String message) {
        super("No ingredient found for name: " + message);
    }

    public MissingIngredientException(Ingredient ingredient, String message) {
        super(message);
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public String getName() {
        return ingredient.getName();
    }
}
