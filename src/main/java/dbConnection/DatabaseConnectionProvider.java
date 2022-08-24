package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionProvider {

    private static final DatabaseConnectionProvider connector = new DatabaseConnectionProvider();

    private Connection connection;

    private DatabaseConnectionProvider() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnectionProvider getConnector() {
        return connector;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventRegister", "root", "root");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
