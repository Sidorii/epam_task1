package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.SaladVisitor;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Packing;

import java.util.Set;

public class SaladDish implements Dish {

    private Salad salad;
    private Packing packing;

    public SaladDish(Salad salad, Packing packing) {
        this.salad = salad;
        this.packing = packing;
    }

    @Override
    public Integer getId() {
        return salad.getId();
    }

    @Override
    public float getCalories() {
        return salad.getCalories();
    }

    @Override
    public double getTotalWeight() {
        return salad.getWeight() + packing.getWeight();
    }

    @Override
    public String getName() {
        return salad.getName();
    }

    @Override
    public float getPrice() {
        return salad.getPrice() + packing.getPrice();
    }

    @Override
    public double getWeight() {
        return salad.getWeight();
    }

    @Override
    public String getDescription() {
        return salad.getDescription() + packing.getDescription();
    }

    @Override
    public boolean isVegan() {
        return salad.isVegan();
    }

    @Override
    public Set<Ingredient> getIngredients() {
        return salad.getIngredients();
    }

    public Packing getPacking() {
        return packing;
    }

    @Override
    public void acceptVisitor(SaladVisitor visitor) {
        visitor.visitSalad(salad);
        visitor.visitPacking(packing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaladDish)) return false;

        SaladDish saladDish = (SaladDish) o;

        if (!salad.equals(saladDish.salad)) return false;
        return getPacking().equals(saladDish.getPacking());
    }

    @Override
    public int hashCode() {
        int result = salad.hashCode();
        result = 31 * result + getPacking().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SaladDish{" +
                "salad=" + salad +
                ", packing=" + packing +
                '}';
    }
}
