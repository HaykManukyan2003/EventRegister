package manager;

import dbConnection.DatabaseConnectionProvider;
import enums.EventType;
import model.Event;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class EventManager {

    private final Connection connection;

    public EventManager() {
        connection = DatabaseConnectionProvider.getConnector().getConnection();
    }

    public void addEvent(Event event) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO event(`name`,place,isOnline,price,event_type) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getPlace());
        preparedStatement.setBoolean(3, event.isOnline());
        preparedStatement.setDouble(4, event.getPrice());
        preparedStatement.setString(5, event.getEventType().name());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) event.setId(resultSet.getInt(1));
    }

    public List<Event> showEvents() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM event");

        List<Event> events = new LinkedList<>();

        while (resultSet.next()) {
            Event event = new Event();
            event.setId(resultSet.getInt("id"));
            event.setName(resultSet.getString("name"));
            event.setPlace(resultSet.getString("place"));
            event.setOnline(resultSet.getBoolean("isOnline"));
            event.setPrice(resultSet.getDouble("price"));
            event.setEventType(EventType.valueOf(resultSet.getString("event_type")));

            events.add(event);
        }
        return events;
    }

}
