package mapreduceminer.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class EventLog {

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Trace> trace;
    public List<Trace> getTrace() { return this.trace; }
    public void setTrace(List<Trace> trace) { this.trace = trace; }
   
    
    private int number;
    public int getNumber() { return this.number; }
    public void setNumber(int number) { this.number = number; }

    public EventLog() { }
}