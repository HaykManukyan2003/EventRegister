package manager;

import dbConnection.DatabaseConnectionProvider;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
    private final Connection connection;

    public UserManager() {
        connection = DatabaseConnectionProvider.getConnector().getConnection();
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(`name`,surname,email,event_id) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setInt(4, user.getEventId());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) user.setId(resultSet.getInt(1));
    }

    public List<User> showUsers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

        List<User> users = new LinkedList<>();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setEventId(resultSet.getInt("event_id"));

            users.add(user);
        }
        return users;
    }

}
