package com.epam.trainee.model;

import com.epam.trainee.model.entities.PackingType;
import com.epam.trainee.model.entities.SaladIngredient;

public interface SaladVisitor {

    void visitIngredient(SaladIngredient ingredient);

    void visitPacking(PackingType packingType);
}
