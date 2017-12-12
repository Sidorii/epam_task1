package ua.epam.trainee.model;

import ua.epam.trainee.model.entities.Ingredient;
import ua.epam.trainee.model.entities.Packing;
import ua.epam.trainee.model.entities.dishes.Salad;

public interface SaladVisitor {

    void visitSalad(Salad salad);

    void visitIngredient(Ingredient ingredient);

    void visitPacking(Packing packing);
}
