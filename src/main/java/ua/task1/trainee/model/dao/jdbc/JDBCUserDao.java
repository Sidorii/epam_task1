package ua.task1.trainee.model.dao.jdbc;

import ua.task1.trainee.model.dao.UserDao;
import ua.task1.trainee.model.dao.jdbc.mappers.UserMapper;
import ua.task1.trainee.model.entities.Role;
import ua.task1.trainee.model.entities.User;
import ua.task1.trainee.model.exceptions.MissingEntityException;

import java.sql.*;

public class JDBCUserDao extends JDBCDao implements UserDao {

    private static final JDBCUserDao INSTANCE = new JDBCUserDao();

    private JDBCUserDao() {
    }

    public static JDBCUserDao getInstance() {
        return INSTANCE;
    }

    //TODO: check duplicated email
    @Override
    public User createUser(User u){
        final String query = "" +
                " INSERT INTO task1.users (name, email, password)" +
                " VALUES (?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.executeUpdate();
            u.setId(getNewUserId(ps.getGeneratedKeys()));
            insertUserRoles(u, connection);
            connection.commit();
            return u;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException(u, "New user registration failed");
        }
    }

    private void insertUserRoles(User user, Connection connection) throws SQLException {
        final String roleQuery = "" +
                " INSERT INTO task1.user_role" +
                " VALUES (?,?)";

        try (PreparedStatement ps = connection.prepareStatement(roleQuery)) {
            for (Role role : user.getRoles()) {
                ps.setInt(1, user.getId());
                ps.setInt(2, role.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private int getNewUserId(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        throw new MissingEntityException("No generated id found for new user");
    }

    @Override
    public User getUserByEmail(String email) {
        final String query = "" +
                " SELECT * " +
                " FROM task1.users" +
                " LEFT JOIN task1.user_role" +
                " ON task1.users.user_id = task1.user_role.u_id" +
                " WHERE email = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new UserMapper().extractFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MissingEntityException("User with email '" + email + "' not found");
        }
    }

    @Override
    public boolean contains(User user) {
        final String query = "" +
                " SELECT COUNT(user_id)" +
                " FROM task1.users" +
                " WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
