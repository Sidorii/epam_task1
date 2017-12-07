package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.dao.IngredientTypeDao;
import com.epam.trainee.model.dao.jdbc.mappers.IngredientTypeMapper;
import com.epam.trainee.model.entities.IngredientType;
import com.epam.trainee.model.exceptions.DuplicatedEntryException;
import com.epam.trainee.model.exceptions.MissingEntityException;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public void testAdd() throws SQLException {
        dao.addType(IngredientType.VEGETABLE);
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
    public void testAddDuplicate() {
        dao.addType(IngredientType.FRUIT);
        dao.addType(IngredientType.FRUIT);
    }

    @Test
    public void testAddMultiply() throws SQLException {
        dao.addType(IngredientType.VEGETABLE);
        dao.addType(IngredientType.FRUIT);
        try (Connection connection = ((JDBCDao) dao).getConnection()) {
            ResultSet rs = connection
                    .createStatement()
                    .executeQuery("SELECT * FROM task1.ingredient_type");

            if (rs.next()) {
                Set<IngredientType> result = new IngredientTypeMapper().extractSetFromResultSet(rs);
                System.out.println(result);
                assertTrue(result.size() == 2);
                assertSame(IngredientType.VEGETABLE, result.toArray()[0]);
                assertSame(IngredientType.FRUIT, result.toArray()[1]);
            } else {
                fail();
            }
        }
    }

    @Test
    public void testGet() {
        dao.addType(IngredientType.MEAT);
        IngredientType result = dao.getType(IngredientType.MEAT.ordinal());
        assertEquals(IngredientType.MEAT, result);
    }

    @Test(expected = MissingEntityException.class)
    public void testTryToGetValueThatNotContainsInDB() {
        dao.getType(IngredientType.MEAT.ordinal());
    }

    @Test(expected = MissingEntityException.class)
    public void testRemove() {
        dao.addType(IngredientType.MEAT);
        dao.removeType(IngredientType.MEAT.ordinal());
        dao.getType(IngredientType.MEAT.ordinal());
    }

    @Test
    public void testUpdate() throws SQLException {
        dao.addType(IngredientType.FRUIT);
        dao.updateType(IngredientType.FRUIT.ordinal(), IngredientType.MEAT);
        IngredientType result = dao.getType(IngredientType.MEAT.ordinal());
        assertEquals(IngredientType.MEAT, result);
    }

    @After
    public void shutDown() throws SQLException {
        try (Connection connection = ((JDBCDao) dao).getConnection()) {
            connection.createStatement()
                    .executeUpdate("DELETE FROM task1.ingredient_type");
        }
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        ((JDBCDao) dao).dataSource.close();
    }
}