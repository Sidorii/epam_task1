package com.epam.trainee.model.dao;

public interface DAOFactory {

    IngredientDao getIngredientDao();

    PackingDao getPackingDao();

    SaladDao getSaladDao();
}
