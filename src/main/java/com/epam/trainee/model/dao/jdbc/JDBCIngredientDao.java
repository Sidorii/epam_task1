package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.jdbc.mappers.ExtractType;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientMapper;
import com.epam.trainee.model.dao.jdbc.mappers.ObjectMapper;
import com.epam.trainee.model.dao.jdbc.transactions.TransactionalConnection;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.exceptions.MissingEntityException;
import com.epam.trainee.model.exceptions.MissingItemException;
import com.epam.trainee.model.utils.SqlUtil;

import java.sql.*;
import java.util.Set;

public class JDBCIngredientDao extends JdbcCrudDao<Ingredient> implements IngredientDao {

    private static final JDBCIngredientDao INSTANCE = new JDBCIngredientDao();

    private JDBCIngredientDao() {
    }

    public static JDBCIngredientDao getInstance() {
        return INSTANCE;
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

    @Override
    protected ResultSet findAll(Statement statement) throws SQLException {
        final String query = "" +
                " SELECT * " +
                " FROM task1.ingredient" +
                " LEFT JOIN task1.ingredient_type " +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id";
        return statement.executeQuery(query);
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
            return getMapper().extractFromResultSet(rs);
        }
    }

    @Override
    public Set<Ingredient> getIngredients(Set<Ingredient> ingredients) {
        final String query = "" +
                " SELECT *" +
                " FROM task1.ingredient " +
                " LEFT JOIN task1.ingredient_type" +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id" +
                " WHERE ingredient_id " + SqlUtil.generateInClause(ingredients.size());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            int position = 1;
            for (Ingredient i : ingredients) {
                ps.setInt(position++, i.getId());
            }
            ResultSet rs = ps.executeQuery();
            rs.next();
            return getMapper().extractSetFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException(ingredients, "One of ingredients is not found");
        }
    }


    private void fillPreparedStatement(PreparedStatement ps, Ingredient ingredient) throws SQLException {
        ps.setString(1, ingredient.getName());
        ps.setDouble(2, ingredient.getWeight());
        ps.setFloat(3, ingredient.getCalories());
        ps.setFloat(4, ingredient.getPrice());
        ps.setBoolean(5, ingredient.isFresh());
        ps.setString(6, ingredient.getDescription());
        ps.setInt(7, ingredient.getType().ordinal());
    }

    @Override
    public Set<Ingredient> findIngredientsById(Set<Integer> idSet) {
        final String query = "" +
                " SELECT * " +
                " FROM task1.ingredient" +
                " LEFT JOIN task1.ingredient_type" +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id" +
                " WHERE ingredient_id " + SqlUtil.generateInClause(idSet.size());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            for(Integer id: idSet){
                ps.setInt(index++, id);
            }
            ResultSet rs = ps.executeQuery();
            rs.next();
            return getMapper().extractSetFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException("Not found entity in id range: " + idSet);
        }
    }

    @Override
    public void mergeIngredientsWeight(Set<Ingredient> ingredients) {
        final String query = "" +
                " UPDATE task1.ingredient SET " +
                " weight = ?" +
                " WHERE ingredient_id = ?";

        try (TransactionalConnection connection = getTransactionalConnection();
             PreparedStatement ps = connection.getConnection().prepareStatement(query)) {
            for (Ingredient ingredient : ingredients) {
                ps.setDouble(1, ingredient.getWeight());
                ps.setInt(2, ingredient.getId());
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

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ingredient.getId());
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
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
