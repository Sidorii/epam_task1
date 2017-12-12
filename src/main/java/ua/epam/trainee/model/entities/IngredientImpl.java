package ua.epam.trainee.model.entities;

import ua.epam.trainee.model.SaladVisitor;

public class IngredientImpl implements Ingredient {

    private Integer id;
    private boolean isFresh;
    private double weight;
    private float calories;
    private float price;
    private String description;
    private String name;
    private IngredientType type;

    private IngredientImpl() {
    }

    @Override
    public void setWeight(double weight) {
        IngredientBuilderImpl.checkRange(weight, "weight");
        this.weight = weight;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isFresh() {
        return isFresh;
    }

    public double getWeight() {
        return weight;
    }

    public float getCalories() {
        return (float) (weight * 0.001 * calories); //TODO: fix Nan producing in some cases
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return (float) (0.001 * weight * price);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientImpl)) return false;

        IngredientImpl that = (IngredientImpl) o;

        if (id != null && that.getId() != null) {
            return id.equals(that.getId());
        }

        if (!getName().equals(that.getName())) return false;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id;
        }

        int result = 31 * (name != null ? name.hashCode() : 0);
        return result + (type != null ? type.hashCode() : 0);
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

    public static IngredientBuilderImpl getIngredientBuilder() {
        return new IngredientBuilderImpl();
    }


    public static class IngredientBuilderImpl
            extends Ingredient.IngredientBuilder<IngredientImpl, IngredientBuilderImpl> {

        private IngredientImpl ingredient;

        private IngredientBuilderImpl() {
            this.ingredient = new IngredientImpl();
        }

        public IngredientImpl createIngredient() {
            ingredient.id = id;
            ingredient.calories = calories;
            ingredient.description = description;
            ingredient.isFresh = isFresh;
            ingredient.name = name;
            ingredient.price = price;
            ingredient.type = type;
            ingredient.weight = weight;

            return ingredient;
        }

        public IngredientBuilderImpl createFrom(Ingredient ingredient) {
            id = ingredient.getId();
            weight = ingredient.getWeight();
            calories = (float) (ingredient.getCalories() / (weight * 0.001)); //TODO: fix weight converting mismatch
            price = (float) (ingredient.getPrice() / (weight * 0.001));
            description = ingredient.getDescription();
            isFresh = ingredient.isFresh();
            name = ingredient.getName();
            type = ingredient.getType();
            return this;
        }
    }
}
