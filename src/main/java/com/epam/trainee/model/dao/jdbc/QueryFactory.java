package com.epam.trainee.model.dao.jdbc;

public interface QueryFactory {

    String getCreateQuery();

    String getReadQuery();

    String getUpdateQuery();

    String getDeleteQuery();
}
