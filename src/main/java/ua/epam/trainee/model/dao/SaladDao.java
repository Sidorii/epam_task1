package ua.epam.trainee.model.dao;

import ua.epam.trainee.model.entities.dishes.Salad;

public interface SaladDao extends GenericDao<Salad> {

    Salad getSaladByName(String name);
}
