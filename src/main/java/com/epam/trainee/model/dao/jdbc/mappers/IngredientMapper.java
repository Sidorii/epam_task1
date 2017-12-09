package com.epam.trainee.model.dao.jdbc.mappers;

import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientImpl;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.entities.dishes.Salad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class IngredientMapper extends ObjectMapper<Ingredient> {

    @Override
    protected Ingredient map(ResultSet rs) throws SQLException {
        return IngredientImpl.getIngredientBuilder()
                .setId(rs.getInt("ingredient_id"))
                .setName(rs.getString("ingredient.name"))
                .setPrice(rs.getFloat("ingredient.price"))
                .setWeight(rs.getDouble("ingredient.weight"))
                .setFresh(rs.getBoolean("ingredient.fresh"))
                .setCalories(rs.getFloat("ingredient.calories"))
                .setDescription( rs.getString("ingredient.description"))
                .setType(extractType(rs))
                .createIngredient();
    }

    //TODO: use strategy for binding entities such as CASCADE, NONE, REMOVE, ADD, etc. (strategy should be in dao)
    private IngredientType extractType(ResultSet rs) {
        try {
            return new IngredientTypeMapper().extractFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ingredient makeUnique(Map<Integer, Ingredient> cache, Ingredient ingredient) {
        cache.putIfAbsent(ingredient.getId(), ingredient);
        return cache.get(ingredient.getId());
    }
}
