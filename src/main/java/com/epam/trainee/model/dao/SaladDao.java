package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.dishes.Salad;

public interface SaladDao extends GenericDao<Salad> {

    Salad getSaladByName(String name);
}
