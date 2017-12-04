package com.epam.trainee.model.entities;

import com.epam.trainee.model.SaladVisitor;

public class MealIngredient implements Ingredient {

    private boolean isFresh;
    private double weight;
    private float calories;
    private float price;
    private String description;
    private String name;
    private IngredientType type;


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

    public static IngredientBuilder getIngredientBuilder(double weight) {
        return new IngredientBuilder(weight);
    }

    static IngredientBuilder getIngredientBuilder() {
        return new IngredientBuilder();
    }

    public static class IngredientBuilder extends Ingredient.IngredientBuilder {

        private MealIngredient ingredient;

        private IngredientBuilder() {
            this.ingredient = new MealIngredient();
        }

        private IngredientBuilder(double weight){
            this();
            setWeight(weight * 0.001);
        }

        public MealIngredient createIngredient() {
            ingredient.calories = calories;
            ingredient.description = description;
            ingredient.isFresh = isFresh;
            ingredient.name = name;
            ingredient.price = price;
            ingredient.type = type;
            ingredient.weight = weight;

            return ingredient;
        }

        @Override
        public IngredientBuilder setName(String name) {
            super.setName(name);
            return this;
        }

        @Override
        public IngredientBuilder setType(IngredientType type) {
            super.setType(type);
            return this;
        }
    }

    @Override
    public String toString() {
        return "SaladIngredient{" +
                "  isFresh=" + isFresh +
                ", weight=" + weight +
                ", calories=" + calories +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
