package com.epam.trainee.model.dao.jdbc.transaction;

import java.sql.SQLException;
import java.sql.Savepoint;

public interface TransactionState extends AutoCloseable{

    void commit() throws SQLException;
}
