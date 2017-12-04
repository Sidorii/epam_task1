package com.epam.trainee.model.entities.dishes;

import com.epam.trainee.model.SaladVisitor;
import com.epam.trainee.model.entities.Meal;
import com.epam.trainee.model.entities.Packing;

public class SaladDish implements Meal {

    private Salad salad;
    private Packing packing;

    public SaladDish(Salad salad, Packing packing) {
        this.salad = salad;
        this.packing = packing;
    }

    @Override
    public float getTotalCalories() {
        return salad.getTotalCalories();
    }

    @Override
    public double getTotalWeight() {
        return salad.getTotalWeight() + packing.getWeight();
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
}
