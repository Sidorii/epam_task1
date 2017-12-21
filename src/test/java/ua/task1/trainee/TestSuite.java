package ua.task1.trainee;

import ua.task1.trainee.model.dao.jdbc.JDBCSaladDaoTest;
import ua.task1.trainee.model.entities.IngredientStorageTest;
import ua.task1.trainee.model.dao.jdbc.JDBCIngredientDaoTest;
import ua.task1.trainee.model.dao.jdbc.JDBCIngredientTypeDaoTest;
import ua.task1.trainee.model.entities.dishes.SaladTest;
import ua.task1.trainee.service.impl.SaladServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.task1.trainee.service.impl.UserServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JDBCIngredientDaoTest.class,
        JDBCIngredientTypeDaoTest.class,
        JDBCSaladDaoTest.class,
        IngredientStorageTest.class,
        SaladTest.class,
        SaladServiceTest.class,
        UserServiceImplTest.class
})
public class TestSuite {
}
