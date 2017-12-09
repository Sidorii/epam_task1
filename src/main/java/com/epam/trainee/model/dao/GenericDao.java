package com.epam.trainee.model.dao;

public interface GenericDao<T> {

    T addEntity(T entity);

    T getEntity(Integer id);

    void updateEntity(T oldEntity);

    void removeEntity(Integer id);

    boolean contains(T entity);
}
