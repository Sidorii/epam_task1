package com.epam.trainee.model.dao;

import com.epam.trainee.model.entities.dishes.Salad;

public interface SaladDao {

    Salad createSalad(Salad salad);

    void removeSalad(String name);

    void updateSalad(Salad salad);

    Salad getSalad(String name);

}
