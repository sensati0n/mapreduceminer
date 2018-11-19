package mapreduceminer.model;

import java.util.List;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;


public class Trace {

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Event> event;
    public List<Event> getEvent() { return this.event; }
    public void setEvent(List<Event> event) { this.event = event; }
  
    public Trace() { }

}