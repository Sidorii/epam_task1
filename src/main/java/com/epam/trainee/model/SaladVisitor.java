package com.epam.trainee.model;

import com.epam.trainee.model.entities.PackingType;
import com.epam.trainee.model.tmpimpl.SaladIngredient;

public interface SaladVisitor {

    void visitIngredient(SaladIngredient ingredient);

    void visitPacking(PackingType packingType);
}
