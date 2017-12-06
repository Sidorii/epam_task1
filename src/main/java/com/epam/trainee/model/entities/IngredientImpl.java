package com.epam.trainee.model.entities;

import com.epam.trainee.model.SaladVisitor;

public class IngredientImpl implements Ingredient {

    private boolean isFresh;
    private double weight;
    private float calories;
    private float price;
    private String description;
    private String name;
    private IngredientType type;

    @Override
    public void setWeight(double weight) {
        IngredientBuilder.checkRange(weight, "weight");
        this.weight = weight;
    }

    public boolean isFresh() {
        return isFresh;
    }

    public double getWeight() {
        return weight;
    }

    public float getCalories() {
        return (float) (weight*0.001*calories);
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return (float) (0.001*weight*price);
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

    public static IngredientBuilder getIngredientBuilder() {
        return new IngredientBuilder();
    }

    public static class IngredientBuilder extends Ingredient.IngredientBuilder {

        private IngredientImpl ingredient;

        private IngredientBuilder() {
            this.ingredient = new IngredientImpl();
        }

        public IngredientImpl createIngredient() {
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

        @Override
        public IngredientBuilder setWeight(double weight) {
            super.setWeight(weight);
            return this;
        }

        public IngredientBuilder createFrom(Ingredient ingredient) {
            weight = ingredient.getWeight();
            calories = (float) (ingredient.getCalories()/(weight*0.001)); //TODO: fix weight converting mismatch
            price = (float) (ingredient.getPrice()/(weight*0.001));
            description = ingredient.getDescription();
            isFresh = ingredient.isFresh();
            name = ingredient.getName();
            type = ingredient.getType();
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientImpl)) return false;

        IngredientImpl that = (IngredientImpl) o;

        if (!getName().equals(that.getName())) return false;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SaladIngredient{" +
                "  isFresh=" + isFresh +
                ", weight=" + weight +
                ", calories=" + getCalories() +
                ", price=" + getPrice() +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
