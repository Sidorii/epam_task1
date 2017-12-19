package com.epam.trainee.service.impl;

import com.epam.trainee.service.IngredientService;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.ServiceFactory;
import com.epam.trainee.service.UserService;

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
