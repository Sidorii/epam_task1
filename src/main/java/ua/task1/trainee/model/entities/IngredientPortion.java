package ua.task1.trainee.model.entities;

public class IngredientPortion implements Ingredient {

    private Ingredient ingredient;
    private final double CONVERTER;
    private double weight;

    public IngredientPortion(Ingredient ingredient, double weight) {
        this(ingredient, weight, .001);
    }

    public IngredientPortion(Ingredient ingredient, double weight, double converter) {
        this.CONVERTER = converter;
        this.ingredient = ingredient;
        setWeight(weight);
    }

    @Override
    public Integer getId() {
        return ingredient.getId();
    }

    @Override
    public float getCalories() {
        return (float) (weight * ingredient.getCalories());
    }

    @Override
    public boolean isFresh() {
        return ingredient.isFresh();
    }

    @Override
    public String getName() {
        return ingredient.getName();
    }

    @Override
    public float getPrice() {
        return (float) (weight * ingredient.getPrice());
    }

    @Override
    public IngredientType getType() {
        return ingredient.getType();
    }

    @Override
    public double getWeight() {
        return weight / CONVERTER;
    }

    @Override
    public void setWeight(double weight) {
        this.weight = weight * CONVERTER;
    }

    @Override
    public String getDescription() {
        return ingredient.getDescription();
    }
}
