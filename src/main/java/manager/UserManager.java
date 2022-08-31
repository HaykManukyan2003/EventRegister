package manager;

import dbConnection.DatabaseConnectionProvider;
import model.User;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
    private final Connection connection;

    private final EventManager eventManager = new EventManager();

    public UserManager() {
        connection = DatabaseConnectionProvider.getConnector().getConnection();
    }

    public void addUser(User user) {
        String sql = "INSERT INTO user(`name`, surname, email, event_id) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getEvent().getId());
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) user.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> showUsers() {
        String sql = "SELECT * FROM user";
        List<User> users = new LinkedList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getById(int id) {
        String sql = "SELECT * FROM user WHERE id = " + id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));
        user.setEvent(eventManager.getById(resultSet.getInt("event_id")));
        return user;
    }

}
