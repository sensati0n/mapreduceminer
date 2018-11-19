package de.ubt.ai4.mapreduceminer.model;


import java.util.ArrayList;
import java.util.List;

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

    public List<Trace> getTraces() {
        return traces;
    }

    public void setTraces(List<Trace> traces) {
        this.traces = traces;
    }

}
