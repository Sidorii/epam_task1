package com.epam.trainee.service.impl;

import com.epam.trainee.service.DishService;
import com.epam.trainee.service.SaladService;
import com.epam.trainee.service.ServiceFactory;

public class ServiceFactoryImpl extends ServiceFactory {

    @Override
    public SaladService getSaladService() {
        return new SaladServiceImpl();
    }

    @Override
    public DishService getDishService() {
        return new DishServiceImpl(getSaladService());
    }
}
