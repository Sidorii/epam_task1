package com.epam.trainee.model.dao.jdbc.mappers;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientImpl;
import com.epam.trainee.model.entities.IngredientType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientMapper extends ObjectMapper<Ingredient> {

    @Override
    protected Ingredient map(ResultSet rs) throws SQLException {
        int id = rs.getInt("ingredient_id");
        String name = rs.getString("name");
        double weight = rs.getDouble("weight");
        float calories = rs.getFloat("calories");
        float price = rs.getFloat("price");
        String description = rs.getString("description");
        boolean isFlesh = rs.getBoolean("fresh");
        IngredientType type = new IngredientTypeMapper().map(rs); //TODO: use strategy for binding entities such as CASCADE, NONE, REMOVE, ADD, etc.

        return IngredientImpl.getIngredientBuilder()
                .setName(name)
                .setPrice(price)
                .setWeight(weight)
                .setFresh(isFlesh)
                .setCalories(calories)
                .setDescription(description)
                .setType(type)
                .createIngredient();
    }

}
