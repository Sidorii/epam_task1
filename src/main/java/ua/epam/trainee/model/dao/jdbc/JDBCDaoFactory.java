package ua.epam.trainee.model.dao.jdbc;

import ua.epam.trainee.model.dao.*;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public IngredientDao getIngredientDao() {
        return JDBCIngredientDao.getInstance();
    }

    @Override
    public IngredientTypeDao getIngredientTypeDao() {
        return JDBCIngredientTypeDao.getInstance();
    }

    @Override
    public PackingDao getPackingDao() {
        return null;
    }

    @Override
    public SaladDao getSaladDao() {
        return JDBCSaladDao.getInstance();
    }
}
