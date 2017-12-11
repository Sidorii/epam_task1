package com.epam.trainee;

import com.epam.trainee.model.dao.jdbc.JDBCIngredientDaoTest;
import com.epam.trainee.model.dao.jdbc.JDBCIngredientTypeDaoTest;
import com.epam.trainee.model.dao.jdbc.JDBCSaladDaoTest;
import com.epam.trainee.model.entities.IngredientStorageTest;
import com.epam.trainee.model.entities.dishes.SaladTest;
import com.epam.trainee.service.impl.SaladServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JDBCIngredientDaoTest.class,
        JDBCIngredientTypeDaoTest.class,
        JDBCSaladDaoTest.class,
        IngredientStorageTest.class,
        SaladTest.class,
        SaladServiceTest.class
})
public class TestSuite {
}
