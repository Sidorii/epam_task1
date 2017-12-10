package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.*;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public IngredientDao getIngredientDao() {
        return new JDBCIngredientDao(getIngredientTypeDao());
    }

    @Override
    public IngredientTypeDao getIngredientTypeDao() {
        return new JDBCIngredientTypeDao();
    }

    @Override
    public PackingDao getPackingDao() {
        return null;
    }

    @Override
    public SaladDao getSaladDao() {
        return new JDBCSaladDao();
    }
}
