package com.epam.trainee.model.dao.jdbc.transactions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class TransactionalConnection implements AutoCloseable {

    private Connection connection;
    private Savepoint savepoint;
    private TransactionState state;

    public TransactionalConnection(Connection connection) {
        this.connection = connection;
        state = new UncommittedState();
    }

    public void commit() throws SQLException {
        state.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        connection.rollback(savepoint);
    }

    @Override
    public void close() {
        try {
            state.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setSavepoint(Savepoint savepoint) {
        this.savepoint = savepoint;
    }

    public Savepoint getSavepoint() {
        return savepoint;
    }

    private class UncommittedState implements TransactionState {

        @Override
        public void commit() throws SQLException {
            connection.commit();
            state = new CommittedState();
        }

        @Override
        public void close() throws Exception {
            connection.rollback();
            connection.close();
        }
    }

    private class CommittedState implements TransactionState {

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void close() throws Exception {
            connection.close();
        }
    }
}
