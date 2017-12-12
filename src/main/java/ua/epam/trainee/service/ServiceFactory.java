package ua.epam.trainee.service;

import ua.epam.trainee.service.impl.ServiceFactoryImpl;

public abstract class ServiceFactory {

    private static ServiceFactory instance;

    public abstract SaladService getSaladService();

    public abstract IngredientService getIngredientService();

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
