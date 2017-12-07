package com.epam.trainee.model.dao.jdbc.mappers;

import com.epam.trainee.model.entities.IngredientType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientTypeMapper extends ObjectMapper<IngredientType> {

    @Override
    protected IngredientType map(ResultSet rs) throws SQLException {
        String name = rs.getString("ingredient_name");
        return IngredientType.valueOf(name);
    }
}
