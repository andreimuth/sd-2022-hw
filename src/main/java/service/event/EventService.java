package service.event;

import model.Event;

import java.util.Date;
import java.util.List;

public interface EventService {

    boolean save(Event event);

    List<Event> findAll();

    List<Event> filterByIdAndDate(Long id, Date from, Date to);

}
