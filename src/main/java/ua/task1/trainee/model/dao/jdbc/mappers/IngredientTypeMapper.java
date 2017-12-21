package ua.task1.trainee.model.dao.jdbc.mappers;

import ua.task1.trainee.model.entities.IngredientType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientTypeMapper extends ObjectMapper<IngredientType> {

    @Override
    protected IngredientType map(ResultSet rs) throws SQLException {
        String name = rs.getString("type_name");
        return IngredientType.valueOf(name);
    }
}
