package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.DaoFactory;
import com.epam.trainee.model.dao.SaladDao;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientMapper;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientTypeMapper;
import com.epam.trainee.model.dao.jdbc.mappers.ObjectMapper;
import com.epam.trainee.model.dao.jdbc.mappers.SaladMapper;
import com.epam.trainee.model.dao.jdbc.transaction.TransactionalConnection;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientImpl;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.entities.dishes.Salad;
import com.epam.trainee.model.exceptions.MissingEntityException;
import com.epam.trainee.model.exceptions.MissingItemException;

import java.io.InputStreamReader;

import org.h2.tools.RunScript;

import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JDBCSaladDao extends JdbcCrudDao<Salad> implements SaladDao {

    @Override
    public Salad getSaladByName(String name) {
        Map<Integer, Ingredient> ingredients = new HashMap<>();
        Map<Integer, Salad> salads = new HashMap<>();

        final String query = "" +
                " SELECT * " +
                " FROM task1.salad " +
                " LEFT JOIN task1.salad_ingredient " +
                " ON task1.salad.salad_id = task1.salad_ingredient.salad_id " +
                " LEFT JOIN task1.ingredient " +
                " ON task1.ingredient.ingredient_id = task1.salad_ingredient.ingredient_id " +
                " LEFT JOIN task1.ingredient_type " +
                " ON task1.ingredient.type_id = task1.ingredient_type.type_id " +
                " WHERE task1.salad.name=?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            IngredientTypeMapper ingredientTypeMapper = new IngredientTypeMapper();
            IngredientMapper ingredientMapper = new IngredientMapper();
            SaladMapper saladMapper = new SaladMapper();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Salad salad = saladMapper.extractFromResultSet(rs);
                IngredientType ingredientType = ingredientTypeMapper.extractFromResultSet(rs);
                Ingredient ingredient = ingredientMapper.extractFromResultSet(rs);
                ingredient = IngredientImpl.getIngredientBuilder()
                        .createFrom(ingredient)
                        .setType(ingredientType)
                        .createIngredient();
                ingredient = ingredientMapper.makeUnique(ingredients, ingredient);
                salad = saladMapper.makeUnique(salads, salad);
                salad.addIngredient(ingredient);
            }
            return salads.values().stream().findFirst().orElseThrow(() -> new MissingEntityException("salad by name " +
                    name + " not found"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingItemException("Salad by name = " + name + " not found");
        }
    }

    public static void main(String[] args) throws SQLException {
        InputStream is = ClassLoader.getSystemResourceAsStream("schema.sql");
        RunScript.execute(JDBCDao.dataSource.getConnection(), new InputStreamReader(is));
        System.out.println(DaoFactory.getInstance().getSaladDao()
                .getSaladByName("cesar"));
    }

    @Override
    protected PreparedStatement prepareCreate(Salad entity, TransactionalConnection connection) throws MissingEntityException, SQLException {
        return null;
    }

    @Override
    public Salad find(Salad entity, Connection connection) throws MissingEntityException {
        return null;
    }

    @Override
    public PreparedStatement prepareRead(Integer id, Connection connection) throws MissingEntityException, SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareUpdate(Salad entity, TransactionalConnection connection) throws SQLException {
        return null;
    }

    @Override
    protected boolean contains(Salad entity, Connection connection) throws SQLException {
        return false;
    }

    @Override
    protected PreparedStatement prepareDelete(Integer id, TransactionalConnection connection) throws SQLException {
        return null;
    }

    @Override
    protected ObjectMapper<Salad> getMapper() {
        return null;
    }
}
