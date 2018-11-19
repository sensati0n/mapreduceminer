package de.ubt.ai4.mapreduceminer.constraint;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.util.Tuple;

public abstract class DoubleEventConstraint extends ConstraintImpl {


    Event eventA;
    Event eventB;

    public DoubleEventConstraint() {}

    public DoubleEventConstraint(Event eventA, Event eventB, ConstraintType type) {
        super(type);
        this.eventA = eventA;
        this.eventB = eventB;
    }


    public Event getEventA() {
        return eventA;
    }

    public void setEventA(Event eventA) {
        this.eventA = eventA;
    }

    public void setEventB(Event eventB) {
        this.eventB = eventB;
    }

    public Event getEventB() {
        return eventB;
    }

    public Tuple<Event> getEventTuple() {
        switch(type)
        {
            case ACTIVATION:
                return new Tuple<>(getEventA().filter(eventIdentifier, additionalAttribute), getEventB().filter(eventIdentifier));
            case TARGET:;
                return new Tuple<>(getEventA().filter(eventIdentifier), getEventB().filter(eventIdentifier, additionalAttribute));
            default:
                return null;
        }

    }

}
