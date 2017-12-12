package ua.epam.trainee.model;

import ua.epam.trainee.model.entities.Ingredient;
import ua.epam.trainee.model.entities.Packing;
import ua.epam.trainee.model.entities.PackingType;
import ua.epam.trainee.model.entities.IngredientImpl;
import ua.epam.trainee.model.entities.dishes.Salad;

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

    public void visitIngredient(IngredientImpl ingredient) {

    }

    public void visitPacking(PackingType packingType) {

    }
}
