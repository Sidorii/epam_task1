package ua.epam.trainee.model.dao.jdbc.transactions;

import java.sql.SQLException;

public interface TransactionState extends AutoCloseable{

    void commit() throws SQLException;
}
