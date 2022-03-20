package repository.event;

import model.Client;
import model.Event;
import model.builder.EventBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class EventRepositoryMySQL implements EventRepository {

    private final Connection connection;

    public EventRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Event event) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("Insert into " + EVENT + " values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertStatement.setLong(1, event.getUserId());
            insertStatement.setString(2, event.getAction());
            insertStatement.setDate(3, new Date(event.getDate().getTime()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            rs.next();
            long eventId = rs.getLong(1);
            event.setId(eventId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from " + EVENT;
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                events.add(getEventFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    private Event getEventFromResultSet(ResultSet resultSet) throws SQLException {
        return new EventBuilder()
                .setId(resultSet.getLong("id"))
                .setUserId(resultSet.getLong("user_id"))
                .setAction(resultSet.getString("action"))
                .setDate(new Date(resultSet.getDate("date").getTime()))
                .build();
    }

    @Override
    public List<Event> filterByIdAndDate(Long id, String from, String to) {
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "select e.id, e.user_id, e.action, e.date from " + USER  + " u join " + EVENT + " e on u.id = e.user_id where u.id = " +
                    id + " and " +
                    "e.date >= \'" + from + "\' and e.date <= \'" + to + "\'";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while(resultSet.next()) {
                Event event = getEventFromResultSet(resultSet);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}
