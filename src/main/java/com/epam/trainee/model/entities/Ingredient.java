package com.epam.trainee.model.entities;

public interface Ingredient extends Meal{

    boolean isFresh();

    IngredientType getType();

    void setWeight(double weight);
    
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
            checkRange(calories, "calories");
            this.calories = calories;
            return (I) this;

        }

        I setPrice(float price) {
            checkRange(price,"price");
            this.price = price;
            return (I) this;
        }

        I setDescription(String description) {
            this.description = description;
            return (I) this;
        }

        I setWeight(double weight) {
            checkRange(weight, "weight");
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

        static void checkRange(double weight, String name) {
            if (weight < 0) {
                throw new IllegalArgumentException("Ingredient " + name + " can't be less then 0");
            }
        }

        abstract T createIngredient();
    }
}
