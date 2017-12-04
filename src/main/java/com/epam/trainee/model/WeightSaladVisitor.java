package com.epam.trainee.model;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Packing;
import com.epam.trainee.model.entities.PackingType;
import com.epam.trainee.model.entities.MealIngredient;
import com.epam.trainee.model.entities.dishes.Salad;

public class WeightSaladVisitor implements SaladVisitor {


    @Override
    public void visitSalad(Salad salad) {

    }

    @Override
    public void visitIngredient(Ingredient ingredient) {

    }

    @Override
    public void visitPacking(Packing packing) {

    }

    public void visitIngredient(MealIngredient ingredient) {

    }

    public void visitPacking(PackingType packingType) {

    }
}
