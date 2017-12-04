package com.epam.trainee.model;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.Packing;
import com.epam.trainee.model.entities.dishes.Salad;

public interface SaladVisitor {

    void visitSalad(Salad salad);

    void visitIngredient(Ingredient ingredient);

    void visitPacking(Packing packing);
}
