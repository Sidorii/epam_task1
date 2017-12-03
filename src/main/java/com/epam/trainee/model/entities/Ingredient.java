package com.epam.trainee.model.entities;

public interface Ingredient extends Item {

    boolean isFresh();

    double getWeight();

    float getCalories();

    IngredientType getType();
    
    abstract class IngredientBuilder<T extends Ingredient,I extends IngredientBuilder>{

        protected boolean isFresh;
        protected double weight;
        protected float calories;
        protected float price;
        protected String description;
        protected String name;
        protected IngredientType type;

        public I setFresh(boolean fresh) {
            isFresh = fresh;
            return (I) this;
        }

        public I setCalories(float calories) {
            this.calories = (float) weight*calories;
            return (I) this;

        }

        public I setPrice(float price) {
            this.price = (float) weight*price;
            return (I) this;
        }

        public I setDescription(String description) {
            this.description = description;
            return (I) this;
        }

        public I setName(String name) {
            this.name = name;
            return (I) this;
        }

        public I setType(IngredientType type) {
            this.type = type;
            return (I) this;
        }

        public abstract T createIngredient();
    }
}
