package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.dao.jdbc.mappers.ExtractType;
import com.epam.trainee.model.dao.jdbc.mappers.ObjectMapper;
import com.epam.trainee.model.dao.jdbc.mappers.SaladMapper;
import com.epam.trainee.model.dao.jdbc.transactions.TransactionalConnection;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.exceptions.DuplicatedEntryException;
import com.epam.trainee.model.exceptions.MissingEntityException;
import com.epam.trainee.model.exceptions.MissingItemException;

import java.sql.*;
import java.util.Iterator;
import java.util.Optional;

public class JDBCSaladDao extends JdbcCrudDao<Salad> implements SaladDao {

    private static final JDBCSaladDao INSTANCE = new JDBCSaladDao();

    private JDBCSaladDao() {
    }

    public static JDBCSaladDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Salad getSaladByName(String name) {
        return getByName(name, getConnection());
    }

    @Override
    protected ResultSet findAll(Statement statement) throws SQLException {
        final String query = "" +
                " SELECT * " +
                " FROM task1.salad " +
                " LEFT JOIN task1.salad_ingredient " +
                " ON task1.salad.salad_id = task1.salad_ingredient.salad_id " +
                " LEFT JOIN task1.ingredient " +
                " ON task1.ingredient.ingredient_id = task1.salad_ingredient.ingredient_id " +
                " LEFT JOIN task1.ingredient_type " +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id ";
        return statement.executeQuery(query);
    }

    private Salad getByName(String name, Connection connection) {
        final String query = "" +
                " SELECT * " +
                " FROM task1.salad " +
                " LEFT JOIN task1.salad_ingredient " +
                " ON task1.salad.salad_id = task1.salad_ingredient.salad_id " +
                " LEFT JOIN task1.ingredient " +
                " ON task1.ingredient.ingredient_id = task1.salad_ingredient.ingredient_id " +
                " LEFT JOIN task1.ingredient_type " +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id " +
                " WHERE task1.salad.s_name=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new SaladMapper(ExtractType.FULL).extractFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingItemException("Salad by name = " + name + " not found");
        }
    }

    @Override
    public Salad addEntity(Salad entity) {
        if (contains(entity)) {
            throw new DuplicatedEntryException(entity);
        }

        try (TransactionalConnection connection = getTransactionalConnection();
             PreparedStatement ps = prepareCreate(entity, connection)) {
            ps.executeBatch();
            entity = find(entity, connection.getConnection());
            connection.commit();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException(entity);
        }
    }

    @Override
    protected PreparedStatement prepareCreate(Salad entity, TransactionalConnection connection)
            throws MissingEntityException, SQLException {
        saveSalad(entity, connection.getConnection());
        Salad saladDemo = getSaladDemo(entity, connection.getConnection());
        entity.setId(saladDemo.getId());
        return prepareIngredientsCreation(entity, connection.getConnection());
    }

    private void saveSalad(Salad salad, Connection connection) throws SQLException {
        final String query = "INSERT INTO task1.salad(s_name, s_description) VALUES (?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, salad.getName());
            ps.setString(2, salad.getDescription());
            ps.execute();
        }
    }

    private Salad getSaladDemo(Salad salad, Connection connection) throws SQLException {
        final String query = "SELECT * FROM task1.salad WHERE s_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, salad.getName());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new SaladMapper(ExtractType.DEMO).extractFromResultSet(rs);
        }
    }

    private PreparedStatement prepareIngredientsCreation(Salad salad, Connection connection) throws SQLException {
        final String query = "INSERT INTO task1.salad_ingredient(salad_id, ingredient_id, weight) VALUES (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);

        Iterator<Ingredient> it = salad.getIngredients().iterator();
        while (it.hasNext()) {
            Ingredient ingredient = it.next();
            ps.setInt(1, salad.getId());
            ps.setInt(2, ingredient.getId());
            ps.setDouble(3, ingredient.getWeight());
            ps.addBatch();
        }
        return ps;
    }

    @Override
    public Salad find(Salad entity, Connection connection) throws MissingEntityException {
        return getByName(entity.getName(), connection);
    }

    @Override
    public PreparedStatement prepareRead(Integer id, Connection connection) throws MissingEntityException, SQLException {
        final String query = "" +
                " SELECT * " +
                " FROM task1.salad " +
                " LEFT JOIN task1.salad_ingredient " +
                " ON task1.salad.salad_id = task1.salad_ingredient.salad_id " +
                " LEFT JOIN task1.ingredient " +
                " ON task1.ingredient.ingredient_id = task1.salad_ingredient.ingredient_id " +
                " LEFT JOIN task1.ingredient_type " +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id " +
                " WHERE task1.salad.salad_id=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Salad salad, TransactionalConnection connection) throws SQLException {
        updateSaladInfo(salad, connection.getConnection());
        return updateDependencies(salad, connection.getConnection());
    }

    private void updateSaladInfo(Salad salad, Connection connection) throws SQLException {
        final String query = "" +
                " UPDATE task1.salad " +
                " SET s_name = ?, s_description = ?" +
                " WHERE salad_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, salad.getName());
            ps.setString(2, salad.getDescription());
            ps.setInt(3, salad.getId());
            ps.execute();
        }
    }

    private PreparedStatement updateDependencies(Salad salad, Connection connection) throws SQLException {
        deleteDependencies(salad.getId(), connection);
        return prepareIngredientsCreation(salad, connection);
    }

    @Override
    protected boolean contains(Salad entity, Connection connection) throws SQLException {
        final String query = "" +
                " SELECT COUNT(salad_id) " +
                " FROM task1.salad" +
                " WHERE salad_id = ? OR s_name = ?";
        Optional<Integer> id = Optional.ofNullable(entity.getId());
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id.orElseGet(() -> -1));
            ps.setString(2, entity.getName());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    @Override
    protected PreparedStatement prepareDelete(Integer id, TransactionalConnection connection) throws SQLException {
        deleteDependencies(id, connection.getConnection());
        return prepareDeleteSalad(id, connection.getConnection());
    }

    private void deleteDependencies(Integer id, Connection connection) throws SQLException {
        final String query = "DELETE FROM task1.salad_ingredient WHERE salad_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private PreparedStatement prepareDeleteSalad(Integer id, Connection connection) throws SQLException {
        final String query = "DELETE FROM task1.salad WHERE salad_id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected ObjectMapper<Salad> getMapper() {
        return new SaladMapper(ExtractType.FULL);
    }
}
