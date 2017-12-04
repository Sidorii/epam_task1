package com.epam.trainee.model.entities;

public interface Ingredient extends Item {

    boolean isFresh();

    float getCalories();

    IngredientType getType();
    
    abstract class IngredientBuilder<T extends Ingredient,I extends IngredientBuilder>{

        boolean isFresh;
        double weight;
        float calories;
        float price;
        String description;
        String name;
        IngredientType type;

        I setFresh(boolean fresh) {
            isFresh = fresh;
            return (I) this;
        }

        I setCalories(float calories) {
            this.calories = (float) weight*calories;
            return (I) this;

        }

        I setPrice(float price) {
            this.price = (float) weight*price;
            return (I) this;
        }

        I setDescription(String description) {
            this.description = description;
            return (I) this;
        }

        I setWeight(double weight) {
            this.weight = weight;
            return (I) this;
        }

        I setName(String name) {
            this.name = name;
            return (I) this;
        }

        I setType(IngredientType type) {
            this.type = type;
            return (I) this;
        }

        abstract T createIngredient();
    }
}
