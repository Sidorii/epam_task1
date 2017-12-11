package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.GenericDao;
import com.epam.trainee.model.dao.jdbc.mappers.ObjectMapper;
import com.epam.trainee.model.dao.jdbc.transactions.TransactionalConnection;
import com.epam.trainee.model.exceptions.DuplicatedEntryException;
import com.epam.trainee.model.exceptions.MissingEntityException;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public abstract class JdbcCrudDao<T> extends JDBCDao implements GenericDao<T>{

    @Override
    public Set<T> getAll() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = findAll(statement);
            rs.next();
            return getMapper().extractSetFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException("No entities found");
        }
    }

    protected abstract ResultSet findAll(Statement statement) throws SQLException;

    @Override
    public T addEntity(T entity) {
        if (contains(entity)) {
            throw new DuplicatedEntryException(entity);
        }

        try (TransactionalConnection connection = getTransactionalConnection();
             PreparedStatement ps =
                     prepareCreate(entity, connection)) {
            ps.execute();
            entity = find(entity, connection.getConnection());
            connection.commit();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException(entity);
        }
    }

    protected abstract PreparedStatement prepareCreate(T entity, TransactionalConnection connection)
            throws MissingEntityException, SQLException;

    public abstract T find(T entity, Connection connection) throws MissingEntityException, SQLException;

    @Override
    public T getEntity(Integer id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid id (id = " + id + ")");
        }

        try (Connection connection = getConnection();
             PreparedStatement ps =
                     prepareRead(id, connection)) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return getMapper().extractFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException("Entity with id = " + id + " not found");
        }
    }

    public abstract PreparedStatement prepareRead(Integer id, Connection connection)
            throws MissingEntityException, SQLException;

    @Override
    public void updateEntity(T oldEntity) {
        try (TransactionalConnection connection = getTransactionalConnection();
             PreparedStatement ps =
                     prepareUpdate(oldEntity, connection)) {
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract PreparedStatement prepareUpdate(T entity, TransactionalConnection connection) throws SQLException;

    @Override
    public void removeEntity(Integer id) {
        try (TransactionalConnection connection = getTransactionalConnection();
             PreparedStatement ps =
                     prepareDelete(id, connection)) {
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean contains(T entity) {
        if (entity == null) {
            return false;
        }
        try (Connection connection = getConnection()) {
            return contains(entity, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected abstract boolean contains(T entity, Connection connection) throws SQLException;

    protected abstract PreparedStatement prepareDelete(Integer id, TransactionalConnection connection) throws SQLException;

    protected abstract ObjectMapper<T> getMapper();
}
