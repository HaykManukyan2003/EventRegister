package manager;

import dbConnection.DatabaseConnectionProvider;
import enums.EventType;
import model.Event;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class EventManager {

    private final Connection connection = DatabaseConnectionProvider.getConnector().getConnection();

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void addEvent(Event event) {
        String sql = "INSERT INTO event(`name`, place, is_online, price, event_type, event_date) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getName());
            ps.setString(2, event.getPlace());
            ps.setBoolean(3, event.isOnline());
            ps.setDouble(4, event.getPrice());
            ps.setString(5, event.getEventType().name());
            ps.setString(6, sdf.format(event.getEventDate()));
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) event.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Event> showEvents() {
        String sql = "SELECT * FROM event";

        List<Event> events = new LinkedList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                events.add(getEventFromResultSet(resultSet));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event getById(int id) {
        String sql = "SELECT * FROM event WHERE id = " + id;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return getEventFromResultSet(resultSet);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Event getEventFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        return Event.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .place(resultSet.getString("place"))
                .isOnline(resultSet.getBoolean("is_online"))
                .price(resultSet.getDouble("price"))
                .eventType(EventType.valueOf(resultSet.getString("event_type")))
                .eventDate(resultSet.getString("event_date") == null ? null : sdf.parse(resultSet.getString("event_date")))
                .build();
    }
}
