package com.epam.trainee.model.dao;

import java.util.Set;

public interface GenericDao<T> {

    T addEntity(T entity);

    T getEntity(Integer id);

    void updateEntity(T oldEntity);

    void removeEntity(Integer id);

    boolean contains(T entity);

    Set<T> getAll();
}
