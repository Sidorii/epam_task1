package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.entities.dishes.Salad;

public class JDBCSaladDao implements SaladDao {

    @Override
    public Salad addEntity(Salad entity) {
        return null;
    }

    @Override
    public Salad getEntity(Integer id) {
        return null;
    }

    @Override
    public void updateEntity(Salad oldEntity) {

    }

    @Override
    public Salad getSaladByName(String name) {
        return null;
    }

    @Override
    public void removeEntity(Integer id) {

    }

    @Override
    public boolean contains(Salad entity) {
        return false;
    }
}
