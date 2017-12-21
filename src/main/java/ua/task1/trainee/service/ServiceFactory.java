package ua.task1.trainee.service;

import ua.task1.trainee.service.impl.ServiceFactoryImpl;

public abstract class ServiceFactory {

    private static ServiceFactory instance;

    public abstract SaladService getSaladService();

    public abstract IngredientService getIngredientService();

    public abstract UserService getUserService();

    public static ServiceFactory getInstance() {
        if (instance == null) {
            synchronized (ServiceFactory.class) {
                if ((instance == null)) {
                    instance = new ServiceFactoryImpl();
                }
            }
        }
        return instance;
    }
}
