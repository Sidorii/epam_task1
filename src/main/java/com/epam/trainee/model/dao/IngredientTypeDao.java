package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.IngredientType;

public interface IngredientTypeDao {

    void addType(IngredientType type);

    void removeType(int id);

    IngredientType getType(int id);

    void updateType(int oldId, IngredientType type);

    boolean contains(IngredientType ingredient);
}
