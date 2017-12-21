package ua.task1.trainee.service.impl;

import ua.task1.trainee.service.IngredientService;
import ua.task1.trainee.service.SaladService;
import ua.task1.trainee.service.ServiceFactory;
import ua.task1.trainee.service.UserService;

public class ServiceFactoryImpl extends ServiceFactory {

    @Override
    public SaladService getSaladService() {
        return SaladServiceImpl.getInstance();
    }

    @Override
    public IngredientService getIngredientService() {
        return IngredientServiceImpl.getInstance();
    }

    @Override
    public UserService getUserService() {
        return UserServiceImpl.getInstance();
    }
}
