package ua.task1.trainee.model.dao;

import ua.task1.trainee.model.dao.jdbc.JDBCDaoFactory;

public abstract class DaoFactory {

    private static DaoFactory instance;

    public abstract UserDao getUserDao();

    public abstract IngredientDao getIngredientDao();

    public abstract IngredientTypeDao getIngredientTypeDao();

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
