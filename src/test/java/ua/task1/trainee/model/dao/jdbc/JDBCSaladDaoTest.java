package ua.task1.trainee.model.dao.jdbc;

import ua.task1.trainee.model.entities.Ingredient;
import ua.task1.trainee.model.dao.SaladDao;
import ua.task1.trainee.model.entities.IngredientImpl;
import ua.task1.trainee.model.entities.IngredientType;
import ua.task1.trainee.model.entities.dishes.Salad;
import ua.task1.trainee.model.exceptions.DuplicatedEntryException;
import ua.task1.trainee.model.exceptions.MissingEntityException;
import org.h2.tools.RunScript;
import org.junit.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JDBCSaladDaoTest {

    private static SaladDao dao;
    private static Connection connection;

    @BeforeClass
    public static void setUp() throws SQLException {
        dao = JDBCSaladDao.getInstance();
        connection = ((JDBCDao) dao).getConnection();
    }

    private static void prepareDatabase(Connection connection) throws SQLException {
        InputStream is = ClassLoader.getSystemResourceAsStream("schema.sql");
        RunScript.execute(connection, new InputStreamReader(is));
        is = ClassLoader.getSystemResourceAsStream("salad_dao_test_schema.sql");
        RunScript.execute(connection, new InputStreamReader(is));
    }

    private Ingredient cucumber;
    private Ingredient potato;
    private Ingredient pork;

    @Before
    public void init() throws SQLException {
        prepareDatabase(connection);
        cucumber = IngredientImpl.getIngredientBuilder()
                .setId(1)
                .setName("cucumber")
                .setWeight(4000)
                .setCalories(300)
                .setPrice(20)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();

        potato = IngredientImpl.getIngredientBuilder()
                .setId(2)
                .setName("potato")
                .setWeight(10000)
                .setCalories(500)
                .setPrice(14)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();
        pork = IngredientImpl.getIngredientBuilder()
                .setId(3)
                .setName("pork")
                .setWeight(6000)
                .setCalories(1100)
                .setPrice(120)
                .setType(IngredientType.VEGETABLE)
                .createIngredient();
    }

    @Test
    public void testAdd() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(cucumber);
        ingredients.add(potato);
        Salad salad = new Salad("new salad", ingredients);
        Salad saladDB = dao.addEntity(salad);

        assertEquals(salad, saladDB);
        assertTrue(saladDB.getId() != null);
    }

    @Test(expected = DuplicatedEntryException.class)
    public void testAddExist() {
        dao.addEntity(new Salad("test salad", new HashSet<>()));
    }

    @Test
    public void testGetEntity() {
        Salad salad = dao.getEntity(1);

        assertEquals(new Integer(1), salad.getId());
        assertEquals("test salad", salad.getName());
        assertEquals("", salad.getDescription());
        assertEquals(3, salad.getIngredients().size());
    }

    @Test(expected = MissingEntityException.class)
    public void testGetEntityWithWrongID() {
        dao.getEntity(100500);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEntityByNullId() {
        dao.getEntity(null);
    }

    @Test
    public void testUpdate() {
        Salad salad = dao.getEntity(1);
        salad.setName("updated");
        salad.setDescription("after update");

        dao.updateEntity(salad);

        Salad updated = dao.getEntity(1);
        assertEquals(salad.getName(), updated.getName());
        assertEquals(salad.getDescription(), updated.getDescription());
    }

    @Test
    public void testUpdateDependencies() {
        Salad salad = dao.getEntity(1);
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(pork);
        Salad newSalad = new Salad("new name", ingredientSet);
        newSalad.setId(salad.getId());

        dao.updateEntity(newSalad);
        newSalad = dao.getEntity(1);

        assertTrue(newSalad.getIngredients().size() == 1);
        assertTrue(newSalad.getIngredients().contains(pork));
        assertEquals("new name", newSalad.getName());
    }

    @Test
    public void testGetByName() {
        Salad saladById = dao.getEntity(1);
        Salad saladByName = dao.getSaladByName(saladById.getName());
        assertEquals(saladById, saladByName);
    }

    @Test(expected = MissingEntityException.class)
    public void testGetByNotExistingName() {
        dao.getSaladByName("salad that not exist");
    }

    @Test(expected = MissingEntityException.class)
    public void testGetByNullName() {
        dao.getSaladByName(null);
    }

    @After
    public void tearDown() throws SQLException {
        prepareDatabase(connection);
    }

    @AfterClass
    public static void destroy() throws SQLException {
        JDBCDao.dataSource.close();
    }
}