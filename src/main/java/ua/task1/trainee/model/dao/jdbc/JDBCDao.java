package ua.task1.trainee.model.dao.jdbc;

import ua.task1.trainee.model.dao.jdbc.transactions.TransactionalConnection;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class JDBCDao {

    private static final String DATASOURCE_PATH = "datasource.properties";

    private static final String URL = "database.url";
    private static final String DRIVER_CLASS_NAME = "database.driver-class";
    private static final String USERNAME = "database.username";
    private static final String PASSWORD = "database.password";
    private static final String MIN_CONNECTION_COUNT = "database.idle.min";
    private static final String MAX_CONNECTION_COUNT = "database.idle.max";
    private static final String AUTO_COMMIT = "database.autocommit";

    static BasicDataSource dataSource;
    private static Properties properties;

    static {
        try (InputStream is = JDBCDao.class.getClassLoader().getResourceAsStream(DATASOURCE_PATH);) {
            dataSource = new BasicDataSource();
            properties = new Properties();
            properties.load(is);
            configureDataSource();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't load datasource properties: " + DATASOURCE_PATH);
        }
    }

    private static void configureDataSource() {
        dataSource.setDriverClassName(properties.getProperty(DRIVER_CLASS_NAME));
        dataSource.setUrl(properties.getProperty(URL));
        dataSource.setUsername(properties.getProperty(USERNAME));
        dataSource.setPassword(properties.getProperty(PASSWORD));
        dataSource.setDefaultAutoCommit(Boolean.valueOf(properties.getProperty(AUTO_COMMIT)));
        Integer minIdle = Integer.valueOf(properties.getProperty(MIN_CONNECTION_COUNT));
        Integer maxIdle = Integer.valueOf(properties.getProperty(MAX_CONNECTION_COUNT));
        if (minIdle != null) {
            dataSource.setMinIdle(minIdle);
        }
        if (maxIdle != null) {
            dataSource.setMaxActive(maxIdle);
        }
    }

    Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't find connection", e);
        }
    }

    TransactionalConnection getTransactionalConnection() {
        return new TransactionalConnection(getConnection());
    }
}
