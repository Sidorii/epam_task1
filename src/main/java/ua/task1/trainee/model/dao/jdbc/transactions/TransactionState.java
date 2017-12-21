package ua.task1.trainee.model.dao.jdbc.transactions;

import java.sql.SQLException;

public interface TransactionState extends AutoCloseable{

    void commit() throws SQLException;
}
