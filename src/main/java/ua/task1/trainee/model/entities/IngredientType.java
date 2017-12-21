package ua.task1.trainee.model.entities;

import ua.task1.trainee.model.dao.IngredientTypeDao;
import ua.task1.trainee.model.dao.DaoFactory;

public enum IngredientType {

    VEGETABLE,
    MEAT,
    FRUIT,
    SAUCE;

    static{
        IngredientTypeDao dao = DaoFactory.getInstance().getIngredientTypeDao();
        for (IngredientType type : values()) {
            if (!dao.contains(type)) {
                dao.addEntity(type);
            }
        }
    }
}
