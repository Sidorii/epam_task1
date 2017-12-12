package com.epam.trainee.model.entities;

import com.epam.trainee.model.dao.IngredientTypeDao;
import com.epam.trainee.model.dao.DaoFactory;

public enum IngredientType {

    VEGETABLE,
    MEAT,
    FRUIT;

    static{
        IngredientTypeDao dao = DaoFactory.getInstance().getIngredientTypeDao();
        for (IngredientType type : values()) {
            if (!dao.contains(type)) {
                dao.addEntity(type);
            }
        }
    }
}
