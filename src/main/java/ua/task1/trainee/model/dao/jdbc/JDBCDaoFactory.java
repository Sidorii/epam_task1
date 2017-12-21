package ua.task1.trainee.model.dao.jdbc;

import ua.task1.trainee.model.dao.*;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public UserDao getUserDao() {
        return JDBCUserDao.getInstance();
    }

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
