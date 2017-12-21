package ua.task1.trainee.model.dao.jdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ExtractStrategy<T> {

    T extract(ResultSet rs) throws SQLException;
}
