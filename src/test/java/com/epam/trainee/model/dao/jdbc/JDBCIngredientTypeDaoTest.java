package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientTypeDao;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientTypeMapper;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.exceptions.DuplicatedEntryException;
import com.epam.trainee.model.exceptions.MissingEntityException;
import org.h2.tools.RunScript;
import org.junit.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import static org.junit.Assert.*;

public class JDBCIngredientTypeDaoTest {


    private static IngredientTypeDao dao;

    @BeforeClass
    public static void init() throws SQLException, IOException {
        dao = new JDBCIngredientTypeDao();
        InputStream is = ClassLoader.getSystemResourceAsStream("schema.sql");
        RunScript.execute(((JDBCDao) dao).getConnection(), new InputStreamReader(is));
        is.close();
    }

    @Test
    @Ignore("is ignored because all instances automatically added during static enum initialization")
    public void testAdd() throws SQLException {
        dao.addEntity(IngredientType.VEGETABLE);
        try (Connection connection = ((JDBCDao) dao).getConnection()) {
            ResultSet rs = connection.createStatement()
                    .executeQuery("SELECT * FROM task1.ingredient_type");
            if (rs.next()) {
                IngredientType resultType = new IngredientTypeMapper().extractFromResultSet(rs);
                assertSame(IngredientType.VEGETABLE, resultType);
            } else {
                fail();
            }
        }
    }

    @Test(expected = DuplicatedEntryException.class)
    @Ignore("is ignored because all instances automatically added during static enum initialization")
    public void testAddDuplicate() {
        dao.addEntity(IngredientType.FRUIT);
        dao.addEntity(IngredientType.FRUIT);
    }

    @Test
    @Ignore("is ignored because all instances automatically added during static enum initialization")
    public void testAddMultiply() throws SQLException {
        dao.addEntity(IngredientType.VEGETABLE);
        dao.addEntity(IngredientType.FRUIT);
        try (Connection connection = ((JDBCDao) dao).getConnection()) {
            ResultSet rs = connection
                    .createStatement()
                    .executeQuery("SELECT * FROM task1.ingredient_type");

            if (rs.next()) {
                Set<IngredientType> result = new IngredientTypeMapper().extractSetFromResultSet(rs);
                assertTrue(result.size() == 2);
                assertTrue(result.contains(IngredientType.VEGETABLE));
                assertTrue(result.contains(IngredientType.FRUIT));
            } else {
                fail();
            }
        }
    }

    @Test
    public void testGet() {
        IngredientType result = dao.getEntity(IngredientType.MEAT.ordinal());
        assertEquals(IngredientType.MEAT, result);
    }

    @Test(expected = MissingEntityException.class)
    public void testTryToGetValueThatNotContainsInDB() {
        dao.getEntity(100500);
    }

    @Test(expected = MissingEntityException.class)
    public void testRemove() {
        dao.removeEntity(IngredientType.MEAT.ordinal());
        dao.getEntity(IngredientType.MEAT.ordinal());
    }

    @After
    public void shutDown() throws SQLException {
        try (Connection connection = ((JDBCDao) dao).getConnection()) {
            connection.createStatement()
                    .executeUpdate("DELETE FROM task1.ingredient_type");
            connection.commit();
        }
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        JDBCDao.dataSource.close();
    }
}