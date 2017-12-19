package com.epam.trainee.model.dao.jdbc.mappers;

import com.epam.trainee.model.entities.Role;
import com.epam.trainee.model.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper extends ObjectMapper<User> {

    @Override
    protected User map(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"));
        user.setId(rs.getInt("user_id"));

        do {
            user.addRole(new Role(
                    rs.getInt("r_id"),
                    rs.getString("role_name")
            ));
        } while (rs.next());
        return user;
    }
}
