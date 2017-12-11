package com.epam.trainee.service;

import com.epam.trainee.service.impl.ServiceFactoryImpl;

public abstract class ServiceFactory {

    private static ServiceFactory instance;

    public abstract SaladService getSaladService();

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
