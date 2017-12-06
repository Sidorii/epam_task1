package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.PackingDao;
import com.epam.trainee.model.dao.SaladDao;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public IngredientDao getIngredientDao() {
        return new JDBCIngredientDao();
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
