package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientDao;
import com.epam.trainee.model.dao.jdbc.mappers.ExtractType;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientMapper;
import com.epam.trainee.model.entities.Ingredient;
import com.epam.trainee.model.entities.IngredientImpl;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.exceptions.MissingEntityException;
import com.epam.trainee.model.exceptions.MissingItemException;
import org.h2.tools.RunScript;
import org.junit.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class JDBCIngredientDaoTest {

    private static IngredientDao dao;
    private static Connection connection;
    private Ingredient pork;
    private Ingredient cucumber;
    private Ingredient orange;

    @BeforeClass
    public static void setUp() throws SQLException {
        dao = JDBCIngredientDao.getInstance();
        connection = ((JDBCDao) dao).getConnection();
        InputStream is = ClassLoader.getSystemResourceAsStream("schema.sql");
        RunScript.execute(connection, new InputStreamReader(is));
    }

    @Before
    public void init() {
        pork = IngredientImpl.getIngredientBuilder()
                .setType(IngredientType.MEAT)
                .setName("Pork")
                .setWeight(4500)
                .setPrice(450)
                .setCalories(5000)
                .setFresh(true)
                .setDescription("Just a pork")
                .createIngredient();
        cucumber = IngredientImpl.getIngredientBuilder()
                .setType(IngredientType.VEGETABLE)
                .setName("Cucumber")
                .setWeight(1000)
                .setPrice(1000)
                .setCalories(1000)
                .setFresh(true)
                .setDescription("Cucumber - very simple guy")
                .createIngredient();
        orange = IngredientImpl.getIngredientBuilder()
                .setType(IngredientType.FRUIT)
                .setName("Orange")
                .setWeight(1000)
                .setPrice(1000)
                .setCalories(1000)
                .setDescription("Orange - like chilling")
                .createIngredient();
    }

    @Test
    public void testAddIngredient() throws SQLException {
        dao.addEntity(pork);

        ResultSet rs = connection.createStatement().
                executeQuery("" +
                        "SELECT * " +
                        "FROM task1.ingredient " +
                        "LEFT JOIN task1.ingredient_type " +
                        "ON task1.ingredient.type_id = task1.ingredient_type.type_id");
        rs.next();
        Ingredient result = new IngredientMapper(ExtractType.FULL).extractFromResultSet(rs);
        assertEquals(pork, result);
    }

    @Test
    public void testGetIngredient() throws SQLException {
        dao.addEntity(pork);
        ResultSet rs = connection.createStatement()
                .executeQuery("" +
                        "SELECT * " +
                        "FROM task1.ingredient " +
                        "WHERE i_name = 'Pork'");
        rs.next();
        Ingredient order = new IngredientMapper(ExtractType.DEMO).extractFromResultSet(rs);

        Ingredient result = dao.getEntity(order.getId());
        assertEquals(pork, result);
    }

    @Test(expected = MissingEntityException.class)
    public void testGetMissedIngredient() {
        Ingredient ingredient = IngredientImpl.getIngredientBuilder()
                .setId(100500)
                .createIngredient();
        dao.getEntity(ingredient.getId());
    }

    @Test
    public void testGetIngredientByName() {
        dao.addEntity(pork);
        Ingredient byName = dao.getIngredientByName("Pork");
        assertEquals(pork, byName);
    }

    @Test(expected = MissingItemException.class)
    public void testGetIngredientByMissingName() {
        dao.getIngredientByName("test");
    }

    @Test
    public void testRemoveIngredient() throws SQLException {
        dao.addEntity(pork);
        ResultSet rs = connection.createStatement()
                .executeQuery("" +
                        "SELECT * " +
                        "FROM task1.ingredient " +
                        "WHERE i_name = 'Pork'");
        rs.next();
        Ingredient order = new IngredientMapper(ExtractType.DEMO).extractFromResultSet(rs);

        dao.removeEntity(order.getId());

        rs = connection.createStatement()
                .executeQuery("" +
                        "SELECT COUNT(ingredient_id)" +
                        " FROM task1.ingredient " +
                        " LEFT JOIN task1.ingredient_type " +
                        " ON task1.ingredient.type_id = task1.ingredient_type.type_id" +
                        " WHERE i_name = 'Pork'");
        rs.next();
        int foundRows = rs.getInt(1);
        assertTrue(foundRows == 0);
    }

    @Test
    public void testRemoveIngredientWithWrongId() throws SQLException {
        dao.addEntity(pork);
        Ingredient ingredient1 = IngredientImpl.getIngredientBuilder()
                .setId(100500)
                .createIngredient();
        dao.removeEntity(ingredient1.getId());

        ResultSet rs = connection.createStatement()
                .executeQuery("" +
                        "SELECT COUNT(ingredient_id) " +
                        "FROM task1.ingredient " +
                        "LEFT JOIN task1.ingredient_type " +
                        "ON task1.ingredient.type_id = task1.ingredient_type.type_id " +
                        "WHERE i_name = 'Pork'");
        rs.next();
        int foundRows = rs.getInt(1);
        assertTrue(foundRows == 1);
    }

    @Test
    public void testUpgradeIngredient() {
        dao.addEntity(pork);
        pork = dao.getIngredientByName("Pork");
        Ingredient updatedIngredient = IngredientImpl.getIngredientBuilder()
                .setId(pork.getId())
                .setName("updated")
                .setType(IngredientType.FRUIT)
                .setFresh(false)
                .setPrice(1000)
                .setWeight(1000)
                .setDescription("updated")
                .setCalories(200)
                .createIngredient();
        dao.updateEntity(updatedIngredient);
        pork = dao.getIngredientByName("updated");
        assertEquals("updated", pork.getName());
        assertEquals(IngredientType.FRUIT, pork.getType());
        assertFalse(pork.isFresh());
        assertEquals(1000, pork.getPrice(), 0.001);
        assertEquals(1000d, pork.getWeight(), 0.001);
        assertEquals("updated", pork.getDescription());
        assertEquals(200, pork.getCalories(), 0.001);
    }

    @Test
    public void testGetIngredientsSet() {
        Set<Ingredient> ingredients = new HashSet<>();
        dao.addEntity(pork);
        dao.addEntity(cucumber);
        dao.addEntity(orange);

        pork = dao.getIngredientByName(pork.getName());
        cucumber = dao.getIngredientByName(cucumber.getName());
        ingredients.add(pork);
        ingredients.add(cucumber);

        Set<Ingredient> result = dao.getIngredients(ingredients);
        assertTrue(result.size() == 2);
        assertTrue(result.contains(pork));
        assertTrue(result.contains(cucumber));
        assertFalse(result.contains(orange));
    }

    @Test
    public void testBatchUpdate() {
        Set<Ingredient> ingredients = new HashSet<>();
        dao.addEntity(pork);
        dao.addEntity(cucumber);
        dao.addEntity(orange);

        pork = dao.getIngredientByName(pork.getName());
        cucumber = dao.getIngredientByName(cucumber.getName());
        orange = dao.getIngredientByName(orange.getName());
        pork = IngredientImpl.getIngredientBuilder()
                .createFrom(pork)
                .setWeight(100500)
                .setName("updated1")
                .createIngredient();
        cucumber = IngredientImpl.getIngredientBuilder()
                .createFrom(cucumber)
                .setName("updated2")
                .setWeight(100501)
                .createIngredient();
        orange = IngredientImpl.getIngredientBuilder()
                .createFrom(orange)
                .setName("updated3")
                .setWeight(100502)
                .createIngredient();
        ingredients.add(pork);
        ingredients.add(cucumber);
        ingredients.add(orange);

        dao.mergeIngredientsWeight(ingredients);

        pork = dao.getIngredientByName("Pork");
        cucumber = dao.getIngredientByName("Cucumber");
        orange = dao.getIngredientByName("Orange");
        assertNotEquals("updated1", pork.getName());
        assertNotEquals("updated2", cucumber.getName());
        assertNotEquals("updated3", orange.getName());
        assertEquals(100500, pork.getWeight(), 0.001);
        assertEquals(100501, cucumber.getWeight(), 0.001);
        assertEquals(100502, orange.getWeight(), 0.001);
    }

    @After
    public void cleanUp() throws SQLException {
        connection.createStatement()
                .executeUpdate("DELETE FROM task1.ingredient");
        connection.commit();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        JDBCDao.dataSource.close();
    }
}