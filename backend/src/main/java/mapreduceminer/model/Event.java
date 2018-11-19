package mapreduceminer.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Event {

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Attribute> string;
    public List<Attribute> getString() { return this.string; }
    public void setString(List<Attribute> string) { this.string = string; }

    public Event() { }


}