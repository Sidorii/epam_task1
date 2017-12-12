package ua.epam.trainee.service.impl;

import ua.epam.trainee.service.IngredientService;
import ua.epam.trainee.service.SaladService;
import ua.epam.trainee.service.ServiceFactory;

public class ServiceFactoryImpl extends ServiceFactory {

    @Override
    public SaladService getSaladService() {
        return SaladServiceImpl.getInstance();
    }

    @Override
    public IngredientService getIngredientService() {
        return IngredientServiceImpl.getInstance();
    }
}
