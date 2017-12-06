package com.epam.trainee.model.dao;

import com.epam.trainee.model.dao.jdbc.JDBCDaoFactory;

public abstract class DaoFactory {

    private static DaoFactory instance;

    public abstract IngredientDao getIngredientDao();

    public abstract PackingDao getPackingDao();

    public abstract SaladDao getSaladDao();

    public static DaoFactory getInstance() {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    instance = new JDBCDaoFactory();
                }
            }
        }
        return instance;
    }

    public static void setInstance(DaoFactory daoFactory) {
        instance = daoFactory;
    }
}
