package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientMapper;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientImpl;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.exceptions.MissingItemException;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JDBCIngredientDaoTest {

    private static IngredientDao dao;
    private static Connection connection;

    @BeforeClass
    public static void setUp() throws SQLException {
        dao = new JDBCIngredientDao(new JDBCIngredientTypeDao());
        connection = ((JDBCDao) dao).getConnection();
        InputStream is = ClassLoader.getSystemResourceAsStream("schema.sql");
        RunScript.execute(connection, new InputStreamReader(is));
    }

    @Test
    public void testAddIngredient() throws SQLException {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setType(IngredientType.MEAT)
                .setName("Pork")
                .setWeight(4500)
                .setPrice(450)
                .setCalories(5000)
                .setFresh(true)
                .setDescription("Just a pork")
                .createIngredient();
        dao.addIngredient(ingredient);

        ResultSet rs = connection.createStatement().
                executeQuery("SELECT * FROM task1.ingredient " +
                        "LEFT JOIN task1.ingredient_type " +
                        "ON task1.ingredient.type_id = task1.ingredient_type.type_id");
        rs.next();
        Ingredient result = new IngredientMapper().extractFromResultSet(rs);
        assertEquals(ingredient, result);
    }

    @Test
    public void testGetIngredient() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setType(IngredientType.MEAT)
                .setName("Pork")
                .setWeight(4500)
                .setPrice(450)
                .setCalories(5000)
                .setFresh(true)
                .setDescription("Just a pork")
                .createIngredient();
        dao.addIngredient(ingredient);

        Ingredient order = IngredientImpl.getIngredientBuilder()
                .setName("Pork")
                .setId(1)
                .createIngredient();
        Ingredient result = dao.getIngredient(order);
        assertEquals(ingredient, result);
    }

    @Test(expected = MissingItemException.class)
    public void testMissedIngredient() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setId(100500)
                .createIngredient();
        dao.getIngredient(ingredient);
    }

    @Test
    public void testRemoveIngredient() throws SQLException {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setName("Pork")
                .setType(IngredientType.MEAT)
                .setWeight(1000)
                .createIngredient();
        dao.addIngredient(ingredient);
        Ingredient ingredient1 = IngredientImpl.getIngredientBuilder()
                .setId(1)
                .createIngredient();
        dao.removeIngredient(ingredient1);

        ResultSet rs = connection.createStatement()
                .executeQuery("SELECT COUNT(ingredient_id) FROM task1.ingredient " +
                        " LEFT JOIN task1.ingredient_type ON task1.ingredient.type_id = task1.ingredient_type.type_id" +
                        " WHERE name = 'Pork'");
        rs.next();
        int foundRows = rs.getInt(1);
        assertTrue(foundRows == 0);
    }

    @Test
    public void testRemoveIngredientWithWrongId() throws SQLException {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setName("Pork")
                .setType(IngredientType.MEAT)
                .createIngredient();
        dao.addIngredient(ingredient);
        Ingredient ingredient1 = IngredientImpl.getIngredientBuilder()
                .setId(100500)
                .createIngredient();
        dao.removeIngredient(ingredient1);

        ResultSet rs = connection.createStatement()
                .executeQuery("SELECT COUNT(ingredient_id) FROM task1.ingredient " +
                        " LEFT JOIN task1.ingredient_type ON task1.ingredient.type_id = task1.ingredient_type.type_id" +
                        " WHERE name = 'Pork'");
        rs.next();
        int foundRows = rs.getInt(1);
        assertTrue(foundRows == 1);
    }


    @Test
    public void testRemoveMissedIngredient() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setId(1)
                .createIngredient();
        dao.removeIngredient(ingredient);
    }

    @After
    public void cleanUp() throws SQLException {
        connection.createStatement()
                .executeUpdate("DELETE FROM task1.ingredient");
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        ((JDBCDao) dao).dataSource.close();
    }
}