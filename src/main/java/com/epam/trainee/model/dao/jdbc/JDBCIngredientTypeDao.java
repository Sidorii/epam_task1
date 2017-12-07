package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientTypeDao;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientTypeMapper;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.exceptions.DuplicatedEntryException;
import com.epam.trainee.model.exceptions.MissingEntityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCIngredientTypeDao extends JDBCDao implements IngredientTypeDao {

    @Override
    public void addType(IngredientType type) {
        if (contains(type)) {
            throw new DuplicatedEntryException(type);
        }
        final String query = "INSERT INTO task1.ingredient_type VALUES(?,?)";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, type.ordinal());
            ps.setString(2, type.name());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException(type);
        }
    }

    @Override
    public void removeType(int id) {
        final String query = "DELETE FROM task1.ingredient_type WHERE type_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IngredientType getType(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("id (id = " + id + ") cant be less then 0.");
        }
        final String query = "SELECT * FROM task1.ingredient_type WHERE type_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new IngredientTypeMapper().extractFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException("Ingredient with id = " + id + " not found");
        }
    }

    @Override
    public void updateType(int oldId, IngredientType type) {
        final String query = "UPDATE task1.ingredient_type SET type_id = ?, ingredient_name = ? WHERE type_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, type.ordinal());
            ps.setString(2, type.name());
            ps.setInt(3, oldId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean contains(IngredientType ingredientType) {
        final String query = "SELECT COUNT(type_id) FROM task1.ingredient_type WHERE type_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ingredientType.ordinal());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
