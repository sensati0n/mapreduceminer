package de.ai4.mapreduceminer.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "log")
public class EventLog {


    private List<Trace> traces;

    public EventLog(List<Trace> traces)
    {
        this.traces = traces;
    }

    public EventLog() {
        this.traces = new ArrayList<Trace>();
    }

    public void addTrace(Trace trace)
    {
        this.traces.add(trace);
    }

    @XmlElement(name="trace")
    public List<Trace> getTraces() {
        return traces;
    }

    public void setTraces(List<Trace> traces) {
        this.traces = traces;
    }

}
