package de.ubt.ai4.mapreduceminer.util;

import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.model.EventLog;
import de.ubt.ai4.mapreduceminer.model.Trace;

import java.util.HashMap;
import java.util.Map;

public class LogInfo {

    public Map<Event, Integer> occurrences;

    public LogInfo(EventLog log, Configuration configuration) {

        this.occurrences = new HashMap<>();

        for(Trace trace : log.getTraces())
            for(Event event : trace.getEvents())
            {
                Event eventSP = event.filter(configuration.getEventIdentifier());
                Event eventMP = event.filter(configuration.getEventIdentifier(), configuration.getAdditionalAttribute());

                if(occurrences.containsKey(eventSP)) {
                    int current = occurrences.get(eventSP);
                    occurrences.remove(eventSP);
                    occurrences.put(eventSP, current++);
                }
                else
                    occurrences.put(eventSP, 1);

                if(occurrences.containsKey(eventMP)) {
                    int current = occurrences.get(eventMP);
                    occurrences.remove(eventMP);
                    occurrences.put(eventMP, current++);
                }
                else
                    occurrences.put(eventMP, 1);
            }

    }
}
