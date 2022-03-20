package repository.event;

import model.Event;

import java.util.List;

public interface EventRepository {

    boolean save(Event event);

    List<Event> findAll();

    List<Event> filterByIdAndDate(Long id, String from, String to);

}
