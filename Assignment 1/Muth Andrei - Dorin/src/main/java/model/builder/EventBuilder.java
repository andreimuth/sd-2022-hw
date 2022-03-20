package model.builder;

import model.Event;

import java.util.Date;

public class EventBuilder {

    private Event event;

    public EventBuilder() {
        event = new Event();
    }

    public EventBuilder setId(Long id) {
        event.setId(id);
        return this;
    }

    public EventBuilder setUserId(Long id) {
        event.setUserId(id);
        return this;
    }

    public EventBuilder setAction(String action) {
        event.setAction(action);
        return this;
    }

    public EventBuilder setDate(Date date) {
        event.setDate(date);
        return this;
    }

    public Event build() {
        return event;
    }

}
