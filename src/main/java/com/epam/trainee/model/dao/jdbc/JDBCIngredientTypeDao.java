package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientTypeDao;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientTypeMapper;
import com.epam.trainee.model.dao.jdbc.transactions.TransactionalConnection;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.dao.jdbc.mappers.ObjectMapper;
import com.epam.trainee.model.exceptions.MissingEntityException;

import java.sql.*;

public class JDBCIngredientTypeDao extends JdbcCrudDao<IngredientType> implements IngredientTypeDao {

    private static final JDBCIngredientTypeDao INSTANCE = new JDBCIngredientTypeDao();

    private JDBCIngredientTypeDao() {
    }

    public static JDBCIngredientTypeDao getInstance() {
        return INSTANCE;
    }

    @Override
    public void updateEntity(IngredientType type) {
       /*empty because enum can't be updated in runtime*/
    }

    @Override
    protected ResultSet findAll(Statement statement) throws SQLException {
        final String query = "SELECT * FROM task1.ingredient_type";
        return statement.executeQuery(query);
    }

    @Override
    public boolean contains(IngredientType ingredientType, Connection connection) throws SQLException {
        final String query = "SELECT COUNT(type_id) FROM task1.ingredient_type WHERE type_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ingredientType.ordinal());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    @Override
    protected PreparedStatement prepareCreate(IngredientType type, TransactionalConnection connection)
            throws SQLException {

        final String query = "INSERT INTO task1.ingredient_type VALUES(?,?)";
        PreparedStatement ps = connection.getConnection().prepareStatement(query);
        ps.setInt(1, type.ordinal());
        ps.setString(2, type.name());
        return ps;
    }

    @Override
    public IngredientType find(IngredientType entity, Connection connection) throws MissingEntityException {
        return entity;
    }

    @Override
    public PreparedStatement prepareRead(Integer id, Connection connection) throws SQLException {
        final String query = "SELECT * FROM task1.ingredient_type WHERE type_id = ?";
        return prepareOnlyByIdStatement(id, connection.prepareStatement(query));
    }

    private PreparedStatement prepareOnlyByIdStatement(Integer id, PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(IngredientType entity, TransactionalConnection connection) {
        return null;
    }

    @Override
    protected PreparedStatement prepareDelete(Integer id, TransactionalConnection connection) throws SQLException {
        final String query = "DELETE FROM task1.ingredient_type WHERE type_id = ?";
        return prepareOnlyByIdStatement(id, connection.getConnection().prepareStatement(query));
    }

    @Override
    protected ObjectMapper<IngredientType> getMapper() {
        return new IngredientTypeMapper();
    }
}
