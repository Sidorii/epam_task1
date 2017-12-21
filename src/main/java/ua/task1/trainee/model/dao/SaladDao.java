package ua.task1.trainee.model.dao;

import ua.task1.trainee.model.entities.dishes.Salad;

public interface SaladDao extends GenericDao<Salad> {

    Salad getSaladByName(String name);
}
