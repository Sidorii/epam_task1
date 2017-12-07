package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.IngredientTypeDao;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientMapper;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.exceptions.DuplicatedEntryException;
import com.epam.trainee.model.exceptions.MissingEntityException;
import com.epam.trainee.model.exceptions.MissingItemException;

import javax.sql.rowset.serial.SerialArray;
import java.sql.*;
import java.util.Set;

public class JDBCIngredientDao extends JDBCDao implements IngredientDao {

    private IngredientTypeDao ingredientTypeDao;

    public JDBCIngredientDao(IngredientTypeDao ingredientTypeDao) {
        this.ingredientTypeDao = ingredientTypeDao;
    }

    public void setIngredientTypeDao(IngredientTypeDao ingredientTypeDao) {
        this.ingredientTypeDao = ingredientTypeDao;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        if (contains(ingredient.getId())) {
            throw new DuplicatedEntryException(ingredient);
        }

        final String query = "INSERT INTO task1.ingredient(name, weight, calories, price, fresh, description, type_id)" +
                "VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, ingredient.getName());
            ps.setDouble(2, ingredient.getWeight());
            ps.setFloat(3, ingredient.getCalories());
            ps.setFloat(4, ingredient.getPrice());
            ps.setBoolean(5, ingredient.isFresh());
            ps.setString(6, ingredient.getDescription());
            IngredientType type = ingredient.getType();
            if (type == null) {
                throw new MissingEntityException(ingredient,"Ingredient's type can't be null");
            }
            ps.setInt(7, ingredient.getType().ordinal());

            if (!ingredientTypeDao.contains(ingredient.getType())) {
                ingredientTypeDao.addType(ingredient.getType());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ingredient getIngredient(Ingredient ingredient) {

        final String query = "SELECT DISTINCT * FROM task1.ingredient " +
                "LEFT JOIN task1.ingredient_type " +
                "ON task1.ingredient.type_id = task1.ingredient_type.type_id " +
                "WHERE ingredient_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ingredient.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new IngredientMapper().extractFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingItemException(ingredient);
        }
    }

    @Override
    public void removeIngredient(Ingredient ingredient) {
        final String query = "DELETE FROM task1.ingredient WHERE ingredient_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ingredient.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Ingredient> getIngredients(Set<Ingredient> ingredients) {
        return null;
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {

    }

    @Override
    public void batchUpdate(Set<Ingredient> ingredients) {

    }

    @Override
    public boolean contains(Integer id) {
        return false;
    }
}
