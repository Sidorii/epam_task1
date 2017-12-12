package ua.epam.trainee.model.entities;

public interface Ingredient extends Meal {

    boolean isFresh();

    IngredientType getType();

    void setWeight(double weight);

    @SuppressWarnings(value = "unchecked")
    abstract class IngredientBuilder<T extends Ingredient, I extends IngredientBuilder> {

        Integer id;
        boolean isFresh;
        double weight;
        float calories;
        float price;
        String description;
        String name;
        IngredientType type;

        public I setId(int id) {
            this.id = id;
            return (I) this;
        }

        public I setFresh(boolean fresh) {
            isFresh = fresh;
            return (I) this;
        }

        public I setCalories(float calories) {
            checkRange(calories, "calories");
            this.calories = calories;
            return (I) this;

        }

        public I setPrice(float price) {
            checkRange(price, "price");
            this.price = price;
            return (I) this;
        }

        public I setDescription(String description) {
            this.description = description;
            return (I) this;
        }

        public I setWeight(double weight) {
            checkRange(weight, "weight");
            this.weight = weight;
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

        static void checkRange(double weight, String name) {
            if (weight < 0) {
                throw new IllegalArgumentException("Ingredient " + name + " can't be less then 0");
            }
        }

        public abstract T createIngredient();
    }
}
