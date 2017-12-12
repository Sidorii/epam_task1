package ua.epam.trainee;

import org.junit.Ignore;
import ua.epam.trainee.model.dao.jdbc.JDBCIngredientDaoTest;
import ua.epam.trainee.model.dao.jdbc.JDBCIngredientTypeDaoTest;
import ua.epam.trainee.model.dao.jdbc.JDBCSaladDaoTest;
import ua.epam.trainee.model.entities.IngredientStorageTest;
import ua.epam.trainee.model.entities.dishes.SaladTest;
import ua.epam.trainee.service.impl.SaladServiceTest;
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
