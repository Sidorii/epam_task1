package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.SaladVisitor;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Meal;
import com.epam.trainee.model.entities.Packing;

import java.util.Set;

public class Salad implements Meal {

    private Set<Ingredient> ingredients;
    private Packing packing;

    public Salad(Set<Ingredient> ingredients, Packing packing) {
        this.ingredients = ingredients;
        this.packing = packing;
    }

    public float getTotalCalories() {
        return (float) ingredients.stream()
                .mapToDouble(Ingredient::getCalories)
                .sum();
    }

    public float getTotalWeight() {
        return 0;
    }

    public float getFoodWeight() {
        return 0;
    }

    public boolean isVegan() {
        return false;
    }

    public String getName() {
        return null;
    }

    public float getPrice() {
        return 0;
    }

    public String getDescription() {
        return null;
    }

    public void acceptVisitor(SaladVisitor visitor) {

    }
}
