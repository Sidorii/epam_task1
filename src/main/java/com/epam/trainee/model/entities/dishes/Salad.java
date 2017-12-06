package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.SaladVisitor;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Dish;
import com.epam.trainee.model.entities.Meal;

import java.util.List;
import java.util.Set;

public class Salad implements Meal{

    protected Set<Ingredient> ingredients;
    private String name;

    public Salad(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public float getCalories() {
        return (float) ingredients.stream()
                .mapToDouble(Ingredient::getCalories)
                .sum();
    }

    public double getWeight() {
        return 0;
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return 0;
    }

    public String getDescription() {
        return null;
    }

    public void acceptVisitor(SaladVisitor visitor) {
        ingredients.forEach(ingr -> visitor.visitIngredient((ingr)));
    }


    @Override
    public boolean equals(Object o) {
        //TODO: rewrite equals and hashcode according to real class implementation
        System.out.println("Temporary equals");
        if (this == o) return true;
        if (!(o instanceof Salad)) return false;

        Salad salad = (Salad) o;

        return ingredients != null ? ingredients.equals(salad.ingredients) : salad.ingredients == null;
    }

    @Override
    public int hashCode() {
        System.out.println("Temporary equals");
        return ingredients != null ? ingredients.hashCode() : 0;
    }
}
