package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.IngredientTypeDao;
import com.epam.trainee.model.dao.jdbc.mappers.ExtractType;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientMapper;
import com.epam.trainee.model.dao.jdbc.mappers.ObjectMapper;
import com.epam.trainee.model.dao.jdbc.transaction.TransactionalConnection;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.exceptions.MissingEntityException;
import com.epam.trainee.model.exceptions.MissingItemException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
/*TODO: fix issue when using MissingIngredientException instead of MissingEntityException everywhere*/
public class JDBCIngredientDao extends JdbcCrudDao<Ingredient> implements IngredientDao {

    private IngredientTypeDao ingredientTypeDao;

    public JDBCIngredientDao(IngredientTypeDao ingredientTypeDao) {
        this.ingredientTypeDao = ingredientTypeDao;
    }

    public void setIngredientTypeDao(IngredientTypeDao ingredientTypeDao) {
        this.ingredientTypeDao = ingredientTypeDao;
    }

    @Override
    public Ingredient getIngredientByName(String name) {

        try (Connection connection = getConnection()) {
            return getIngredientByName(name, connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingItemException("Ingredient by name = \'" + name + "\' not found");
        }
    }

    private Ingredient getIngredientByName(String name, Connection connection) throws SQLException {
        final String query = "" +
                " SELECT DISTINCT * " +
                " FROM task1.ingredient" +
                " LEFT JOIN task1.ingredient_type" +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id" +
                " WHERE i_name = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new IngredientMapper(ExtractType.FULL).extractFromResultSet(rs);
        }
    }

    @Override
    public Set<Ingredient> getIngredients(Set<Ingredient> ingredients) {
        final String query = "" +
                " SELECT *" +
                " FROM task1.ingredient " +
                " LEFT JOIN task1.ingredient_type" +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id" +
                " WHERE ingredient_id " + buildInClause(ingredients.size());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            int position = 1;
            for (Ingredient i : ingredients) {
                ps.setInt(position++, i.getId());
            }
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new IngredientMapper(ExtractType.FULL).extractSetFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException(ingredients, "One of ingredients is not found");
        }
    }

    private String buildInClause(int elementsCount) {
        StringBuilder sb = new StringBuilder(" IN (");
        for (int i = 0; i < elementsCount - 1; i++) {
            sb.append("?,");
        }
        sb.append("?)");
        return sb.toString();
    }

    private void fillPreparedStatement(PreparedStatement ps, Ingredient ingredient) throws SQLException {
        ps.setString(1, ingredient.getName());
        ps.setDouble(2, ingredient.getWeight());
        ps.setFloat(3, ingredient.getCalories());
        ps.setFloat(4, ingredient.getPrice());
        ps.setBoolean(5, ingredient.isFresh());
        ps.setString(6, ingredient.getDescription());
        IngredientType type = ingredient.getType();

            /*TODO: fix issue during strategy implementation*/
        if (!ingredientTypeDao.contains(type)) {
            throw new MissingEntityException(type, "Invalid ingredient type");
        }
        ps.setInt(7, ingredient.getType().ordinal());
    }

    @Override
    public void batchUpdate(Set<Ingredient> ingredients) {
        final String query = "" +
                "UPDATE task1.ingredient SET " +
                "i_name = ?, weight = ?, calories = ?, price = ?, fresh = ?," +
                "i_description = ?, type_id = ? " +
                "WHERE ingredient_id = ?";

        try (TransactionalConnection connection = getTransactionalConnection();
             PreparedStatement ps = connection.getConnection().prepareStatement(query)) {
            for (Ingredient ingredient : ingredients) {
                fillPreparedStatement(ps, ingredient);
                ps.setInt(8, ingredient.getId());
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected PreparedStatement prepareCreate(Ingredient ingredient, TransactionalConnection connection)
            throws MissingEntityException, SQLException {
        final String query = "" +
                "INSERT INTO task1.ingredient(i_name, weight, calories, price, fresh, i_description, type_id)" +
                "VALUES (?,?,?,?,?,?,?)";

        PreparedStatement ps = connection.getConnection().prepareStatement(query);
        fillPreparedStatement(ps, ingredient);
        return ps;
    }

    @Override
    public Ingredient find(Ingredient entity, Connection connection) throws MissingEntityException, SQLException {
        return getIngredientByName(entity.getName(), connection);
    }

    @Override
    public PreparedStatement prepareRead(Integer id, Connection connection) throws SQLException {
        final String query = "" +
                " SELECT DISTINCT * " +
                " FROM task1.ingredient " +
                " LEFT JOIN task1.ingredient_type " +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id " +
                " WHERE ingredient_id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Ingredient ingredient, TransactionalConnection connection) throws SQLException {
        final String query = "" +
                "UPDATE task1.ingredient SET " +
                "i_name = ?, weight = ?, calories = ?, price = ?, fresh = ?," +
                "i_description = ?, type_id = ? " +
                "WHERE ingredient_id = ?";

        PreparedStatement ps = connection.getConnection().prepareStatement(query);
        fillPreparedStatement(ps, ingredient);
        ps.setInt(8, ingredient.getId());
        return ps;
    }

    @Override
    protected boolean contains(Ingredient ingredient, Connection connection) throws SQLException {
        if (ingredient.getId() == null) {
            return false;
        }

        final String query = "" +
                " SELECT COUNT(ingredient_id)" +
                " FROM task1.ingredient" +
                " WHERE ingredient_id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, ingredient.getId());
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next() && resultSet.getInt(1) > 0;
    }

    @Override
    protected PreparedStatement prepareDelete(Integer id, TransactionalConnection connection) throws SQLException {
        final String query = "" +
                " DELETE FROM task1.ingredient " +
                " WHERE ingredient_id = ?";

        PreparedStatement ps = connection.getConnection().prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected ObjectMapper<Ingredient> getMapper() {
        return new IngredientMapper(ExtractType.FULL);
    }
}
