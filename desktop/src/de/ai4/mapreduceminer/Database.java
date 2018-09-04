package de.ai4.mapreduceminer;

import de.ai4.mapreduceminer.model.Event;

import java.util.HashMap;
import java.util.Map;


class Tuple<Event> {
    public final Event x;
    public final Event y;

    public Tuple(Event x, Event y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        Tuple<Event> other = (Tuple<Event>) o;

        if(other.x.equals(this.x) && other.y.equals(this.y))
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return x.hashCode()^y.hashCode();
    }

    @Override
    public String toString() {
        return x.toString() + "|" + y.toString();
    }
}

public class Database {
    
    //Eta
    private Map<Event, Integer> eta;


    //Epsilon
    private Map<Event, Integer> epsilon;


    //Sigma
    private Map<Tuple<Event>, Integer> response;
    private Map<Tuple<Event>, Integer> alternateResponse;
    private Map<Tuple<Event>, Integer> chainResponse;
    private Map<Tuple<Event>, Integer> precedence;



    private Map<Tuple<Event>, Integer> alternatePrecedence;
    private Map<Tuple<Event>, Integer> chainPrecedence;

    public Database()
    {
        this.eta = new HashMap<>();
        this.epsilon = new HashMap<>();

        this.response = new HashMap<>();
        this.alternateResponse = new HashMap<>();
        this.chainResponse = new HashMap<>();
        this.precedence = new HashMap<>();
        this.alternatePrecedence = new HashMap<>();
        this.chainPrecedence = new HashMap<>();
    }


    public void addEta(Event event, int value)
    {
        if(this.eta.containsKey(event))
        {
            int currentValue = this.eta.get(event);
            this.eta.remove(event);
            this.eta.put(event, value + currentValue);
        }
        else
        {
            this.eta.put(event, value);

        }

    }

    public void addEpsilon(Event event, int value)
    {
        if(this.epsilon.containsKey(event))
        {
            int currentValue = this.epsilon.get(event);
            this.epsilon.remove(event);
            this.epsilon.put(event, value + currentValue);
        }
        else
        {
            this.epsilon.put(event, value);

        }

    }

    /**
     * this.response contains eventTuple
     * @param eventTuple
     * @param value
     */
    public void addSigma(Constraint constraint, Tuple<Event> eventTuple, int value)
    {
        Map<Tuple<Event>, Integer> constraintMap = null;
        switch(constraint)
        {
            case RESPONSE:
                constraintMap = this.response;
                break;
        }
        if(constraintMap.containsKey(eventTuple)) {
            int currentValue = constraintMap.get(eventTuple);
            constraintMap.remove(eventTuple);
            constraintMap.put(eventTuple, value + currentValue);
        }
        else
        {
            constraintMap.put(eventTuple, value);
        }

    }



    public Map<Event, Integer> getEta() {
        return eta;
    }

    public Map<Event, Integer> getEpsilon() {
        return epsilon;
    }

    public Map<Tuple<Event>, Integer> getResponse() {
        return response;
    }

    public Map<Tuple<Event>, Integer> getAlternateResponse() {
        return alternateResponse;
    }

    public Map<Tuple<Event>, Integer> getChainResponse() {
        return chainResponse;
    }

    public Map<Tuple<Event>, Integer> getPrecedence() {
        return precedence;
    }

    public Map<Tuple<Event>, Integer> getAlternatePrecedence() {
        return alternatePrecedence;
    }

    public Map<Tuple<Event>, Integer> getChainPrecedence() {
        return chainPrecedence;
    }


}
