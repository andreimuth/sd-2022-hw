package service.event;

import model.Event;
import repository.event.EventRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public boolean save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> filterByIdAndDate(Long id, Date from, Date to) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return eventRepository.filterByIdAndDate(id, dateFormat.format(from), dateFormat.format(to));
    }
}
