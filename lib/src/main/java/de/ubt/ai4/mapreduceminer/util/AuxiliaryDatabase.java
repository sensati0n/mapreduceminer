package de.ubt.ai4.mapreduceminer.util;

import de.ubt.ai4.mapreduceminer.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuxiliaryDatabase {

    public static LogInfo logInfo;

    public  List<Event> tasksResponse = new ArrayList<>();
    public  List<Event> tasksAlternateResponse = new ArrayList<>();
    public  List<Event> tasksAlternateSuccession = new ArrayList<>();
    public List<Event> tasksAlternatePrecedence = new ArrayList<>();

    public List<Event> tasksRespondedExistence = new ArrayList<>();
    public List<Event> tasksCoExistence = new ArrayList<>();
    public List<Event> tasksNotCoExistence = new ArrayList<>();
   
    /**
     * Mutual Relation
     */
    public List<Event> tasksNotSuccession = new ArrayList<>();

    public List<Event> tasksSuccessionResponse = new ArrayList<>();


    public boolean breakAlternateResponse = false;
    public boolean breakAlternateSuccession = false;
    public boolean breakChainResponse = false;
    public boolean breakChainPrecedence = false;
    public boolean breakPrecedence = false;
    public boolean breakAlternatePrecedence = false;
    public boolean breakChainSuccession = false;

    public List<Event> uniqueEventsInTrace = new ArrayList<>();
    public List<Tuple<Event>> uniqueEventCombinationInTrace = new ArrayList<>();
    


    public Event first;
    public Event last;

    public int currentI;
    public int currentJ;

    public Map<Event, Integer> eventCounter = new HashMap<>();

    //loginfo

    public void clear() {

      this.tasksResponse.clear();
      this.tasksAlternateResponse.clear();
      this.tasksAlternatePrecedence.clear();
      this.breakAlternateResponse = false;
      this.breakPrecedence = false;
      this.breakAlternatePrecedence = false;

    }

    public void clearEventBased() {

        this.tasksResponse.clear();
        this.tasksSuccessionResponse.clear();
        this.tasksAlternateResponse.clear();
        this.tasksAlternateSuccession.clear();
        this.tasksAlternatePrecedence.clear();
        this.tasksRespondedExistence.clear();
        this.tasksCoExistence.clear();
        this.tasksNotSuccession.clear();
        this.tasksNotCoExistence.clear();
        this.breakAlternateResponse = false;
        this.breakAlternateSuccession = false;
        this.breakPrecedence = false;
        this.breakAlternatePrecedence = false;
        this.breakChainResponse = false;
        this.breakChainPrecedence = false;
        this.breakChainSuccession = false;

      }
  

    public Integer getOccurences(Event event) {
        return logInfo.occurrences.get(event);
    }

    public void addToEventCounter(Event event) {
        if(eventCounter.containsKey(event)) {
            int currentValue = eventCounter.get(event);
            System.out.println(event + ", "+currentValue);            
            eventCounter.remove(event);
            eventCounter.put(event, currentValue+1);
        }
        else {
            eventCounter.put(event, 1);
        }
    }

}
