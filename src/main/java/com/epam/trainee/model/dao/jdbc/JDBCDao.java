package com.epam.trainee.model.dao.jdbc;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class JDBCDao {

    private static final String DATASOURCE_PATH = "datasourse.properties";

    private static final String URL = "database.url";
    private static final String DRIVER_CLASS_NAME = "database.driver-class";
    private static final String USERNAME = "database.username";
    private static final String PASSWORD = "database.password";
    private static final String MIN_CONNECTION_COUNT = "database.idle.min";
    private static final String MAX_CONNECTION_COUNT = "database.idle.max";


    protected BasicDataSource dataSource;
    private Properties properties;

    protected JDBCDao() {
        try {
            dataSource = new BasicDataSource();
            properties = new Properties();
            InputStream is = ClassLoader.getSystemResourceAsStream(DATASOURCE_PATH);
            properties.load(is);
            configDataSource(properties);

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't find datasource properties: " + DATASOURCE_PATH);
        }
    }

    private void configDataSource(Properties properties) {
        String driverName = properties.getProperty(DRIVER_CLASS_NAME);
        String url = properties.getProperty(URL);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);

        Integer minIdle = Integer.valueOf(properties.getProperty(MIN_CONNECTION_COUNT));
        Integer maxIdle = Integer.valueOf(properties.getProperty(MAX_CONNECTION_COUNT));

        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        if (minIdle != null) {
            dataSource.setMinIdle(minIdle);
        }

        if (maxIdle != null) {
            dataSource.setMaxActive(maxIdle);
        }
    }

    protected Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't give connection", e);
        }
    }
}
