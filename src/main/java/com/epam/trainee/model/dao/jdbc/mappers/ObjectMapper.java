package com.epam.trainee.model.dao.jdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class ObjectMapper<T> {

    public Set<T> extractSetFromResultSet(ResultSet rs) throws SQLException {
        Set<T> ingredientTypes = new HashSet<>();
        do {
            ingredientTypes.add(extractFromResultSet(rs));
        }while (rs.next());
        return ingredientTypes;
    }

    public T extractFromResultSet(ResultSet rs) throws SQLException {
        return map(rs);
    }

    protected abstract T map(ResultSet rs) throws SQLException;
}
