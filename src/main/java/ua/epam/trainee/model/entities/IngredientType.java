package ua.epam.trainee.model.entities;

import ua.epam.trainee.model.dao.DaoFactory;
import ua.epam.trainee.model.dao.IngredientTypeDao;

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
