package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.SaladVisitor;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.entities.Meal;

import java.util.Set;

public class Salad implements Meal {

    private Integer id;
    protected Set<Ingredient> ingredients;
    private String name;
    private String description;

    public Salad(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Salad(String name, Set<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isVegan() {
        return ingredients.stream()
                .noneMatch(i -> i.getType() == null ||
                        i.getType().equals(IngredientType.MEAT));
    }

    public float getCalories() {
        return (float) ingredients.stream()
                .mapToDouble(Ingredient::getCalories)
                .sum();
    }

    public double getWeight() {
        return ingredients.stream()
                .mapToDouble(Ingredient::getWeight)
                .sum();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return (float) ingredients.stream()
                .mapToDouble(Ingredient::getPrice)
                .sum();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void acceptVisitor(SaladVisitor visitor) {
        ingredients.forEach(ingr -> visitor.visitIngredient((ingr)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Salad)) return false;

        Salad salad = (Salad) o;
        if (name != null && !name.equals(salad.getName())) return false;
        return ingredients != null ? ingredients.equals(salad.ingredients) : salad.ingredients == null;
    }

    @Override
    public int hashCode() {
        int result = 31 * (name != null ? name.hashCode() : 0);
        return result + (ingredients != null ? ingredients.hashCode() : 0);
    }
}
