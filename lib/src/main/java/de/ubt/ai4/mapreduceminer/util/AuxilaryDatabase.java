package de.ubt.ai4.mapreduceminer.util;

import de.ubt.ai4.mapreduceminer.model.Event;

import java.util.ArrayList;
import java.util.List;

public class AuxilaryDatabase {

    public static LogInfo logInfo;

    public  List<Event> tasksResponse = new ArrayList<>();
    public  List<Event> tasksAlternateResponse = new ArrayList<>();
    public List<Event> tasksAlternatePrecedence = new ArrayList<>();

    public boolean breakAlternateResponse = false;
    public boolean breakPrecedence = false;
    public boolean breakAlternatePrecedence = false;
    public List<Event> uniqueEventsInTrace = new ArrayList<>();

    public Event first;
    public Event last;

    //loginfo

    public void clear() {

      this.tasksResponse.clear();
      this.tasksAlternateResponse.clear();
      this.tasksAlternatePrecedence.clear();
      this.breakAlternateResponse = false;
      this.breakPrecedence = false;
      this.breakAlternatePrecedence = false;
      this.breakPrecedence = false;

    }

    public Integer getOccurences(Event event) {
        return logInfo.occurrences.get(event);
    }

}
