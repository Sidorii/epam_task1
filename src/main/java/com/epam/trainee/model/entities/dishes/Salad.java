package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.SaladVisitor;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Meal;

import java.util.List;

public class Salad implements Meal {

    private List<Ingredient> ingredients;

    public Salad(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public float getTotalCalories() {
        return (float) ingredients.stream()
                .mapToDouble(Ingredient::getCalories)
                .sum();
    }

    public double getTotalWeight() {
        return 0;
    }

    public double getWeight() {
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
        ingredients.forEach(ingr -> visitor.visitIngredient((ingr)));
    }
}
