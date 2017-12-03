package com.epam.trainee.model.tmpimpl;

import com.epam.trainee.model.SaladVisitor;
import com.epam.trainee.model.entities.CookState;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientType;

public class SaladIngredient implements Ingredient {

    private CookState cookState;
    private boolean isFresh;
    private double weight;
    private float calories;
    private float price;
    private String description;
    private String name;
    private IngredientType type;

    public CookState getCookState() {
        return cookState;
    }

    public boolean isFresh() {
        return isFresh;
    }

    public double getWeight() {
        return weight;
    }

    public float getCalories() {
        return calories;
    }

    public String getName() {
        return name;
    }


    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void acceptVisitor(SaladVisitor visitor) {
        visitor.visitIngredient(this);
    }

    public IngredientType getType() {
        return type;
    }

    public static SaladIngredientBuilder getIngredientBuilder(double weight) {
        return new SaladIngredientBuilder(weight);
    }

    public static class SaladIngredientBuilder extends IngredientBuilder<SaladIngredient, SaladIngredientBuilder>{

        private SaladIngredient ingredient;

        public SaladIngredientBuilder(double weight) {
            this.weight = weight*0.001;
            this.ingredient = new SaladIngredient();
        }

        public SaladIngredient createIngredient() {
            ingredient.calories = calories;
            ingredient.cookState = cookState;
            ingredient.description = description;
            ingredient.isFresh = isFresh;
            ingredient.name = name;
            ingredient.price = price;
            ingredient.type = type;
            ingredient.weight = weight;

            return ingredient;
        }
    }

    @Override
    public String toString() {
        return "SaladIngredient{" +
                "cookState=" + cookState +
                ", isFresh=" + isFresh +
                ", weight=" + weight +
                ", calories=" + calories +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
