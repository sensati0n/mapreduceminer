package de.ubt.ai4.mapreduceminer.model;


import java.util.ArrayList;
import java.util.List;

public class Trace {

    private List<Event> events;

    public Trace(List<Event> events)
    {
        this.events = events;
    }

    public Trace() {
        this.events = new ArrayList<Event>();
    }


    public void addEvent(Event event)
    {
        this.events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }


}
