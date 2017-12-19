package com.epam.trainee.model.dao.jdbc;

import com.epam.trainee.model.entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRoleDao extends JDBCDao {

    private static final JdbcRoleDao INSTANCE = new JdbcRoleDao();

    private JdbcRoleDao() {
    }

    public static JdbcRoleDao getInstance() {
        return INSTANCE;
    }

    public void createRole(Role role) {
        final String query = "INSERT INTO task1.role VALUES (?,?)";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, role.getId());
            ps.setString(2, role.name());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't persis role: " + role + " to database");
        }
    }

    public void removeRole(Role role) {
        final String deleteDependencies = "DELETE FROM  task1.user_role WHERE r_id = ?";
        final String deleteRole = "DELETE FROM  task1.role WHERE role_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteDependencies)) {
            ps.setInt(1, role.getId());
            ps.executeUpdate();
            ps.executeUpdate(deleteRole);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't remove role: " + role + " to database");
        }
    }

    public void merge(Role role) {
        final String query = "UPDATE task1.role SET role_name = ? WHERE role_id = ?";
        int updated = 0;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, role.name());
            ps.setInt(2, role.getId());
            updated = ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (updated == 0) {
            createRole(role);
        }
    }
}

