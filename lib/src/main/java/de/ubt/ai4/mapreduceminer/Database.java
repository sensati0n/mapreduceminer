package de.ubt.ai4.mapreduceminer;

import de.ubt.ai4.mapreduceminer.constraint.Constraint;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.Configuration;
import de.ubt.ai4.mapreduceminer.util.Tuple;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<Event, Integer> eta;
    private Map<Event, Integer> epsilon;

    private Map<Tuple<Event>, Integer> twoDimEpsilon;

    public Map<Tuple<Event>, Integer> getTwoDimEpsilon() {
        return twoDimEpsilon;
    }

    private Map<Class, Map<Constraint, Integer>> sigma;


    private Configuration configuration;

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

     public Database(Configuration configuration)
    {
        this.eta = new HashMap<>();
        this.epsilon = new HashMap<>();
        this.twoDimEpsilon = new HashMap<>();

        this.sigma = new HashMap<>();

        for(Class constraint : configuration.getConstraints())
            this.sigma.put(constraint, new HashMap<>());

    this.configuration = configuration;

    }


    public void addEta(Event event, int value)
    {
        if(this.eta.containsKey(event)) {
            int currentValue = this.eta.get(event);
            this.eta.remove(event);
            this.eta.put(event, value + currentValue);
        }
        else
            this.eta.put(event, value);
    }

    public void addEpsilon(Event event, int value)
    {
        if(this.epsilon.containsKey(event)) {
            int currentValue = this.epsilon.get(event);
            this.epsilon.remove(event);
            this.epsilon.put(event, value + currentValue);
        }
        else
            this.epsilon.put(event, value);
    }

    public void addTwoDimEpsilon(Tuple<Event> eventTuple, int value) {
        if (twoDimEpsilon.containsKey(eventTuple)) {
            int currentValue = twoDimEpsilon.get(eventTuple);
            twoDimEpsilon.remove(eventTuple);
            twoDimEpsilon.put(eventTuple, value + currentValue);
        } else {
            twoDimEpsilon.put(eventTuple, value);
        }
    }

    /**
     * this.response contains eventTuple
     * @param eventTuple
     * @param value
     */
 /*   public void addSigma(Class constraint, Tuple<Event> eventTuple, int value)
    {
        Map<Tuple<Event>, Integer> constraintMap = this.sigma.get(constraint);

        if(constraintMap.containsKey(eventTuple)) {
            int currentValue = constraintMap.get(eventTuple);
            constraintMap.remove(eventTuple);
            constraintMap.put(eventTuple, value + currentValue);
        }
        else
            constraintMap.put(eventTuple, value);
    }
*/
    /**
     * this.response contains eventTuple
     * @param constraint
     * @param value
     */
    public void addSigma(Constraint constraint, int value) {

        Map<Constraint, Integer> constraintMap = this.sigma.get(constraint.getClass());
        if(constraintMap.containsKey(constraint)) {
            int currentValue = constraintMap.get(constraint);
            constraintMap.remove(constraint);
            constraintMap.put(constraint, value + currentValue);
        }
        else
            constraintMap.put(constraint, value);
    }

    public Map<Event, Integer> getEta() {
        return eta;
    }

    public Map<Event, Integer> getEpsilon() {
        return epsilon;
    }

    public Map<Constraint, Integer> getSigmaEntry(Class constraint) {
        return this.sigma.get(constraint);
    }


}
