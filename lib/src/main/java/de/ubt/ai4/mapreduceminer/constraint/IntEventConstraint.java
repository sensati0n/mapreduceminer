package de.ubt.ai4.mapreduceminer.constraint;

import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

public abstract class IntEventConstraint extends ConstraintImpl {

    private Event event;
    private Integer n;

    public IntEventConstraint() {
        super();
    }

    public IntEventConstraint(Event event, Integer n, ConstraintType type) {
        super(type);
        this.event = event;
        this.n = n;
    }

    public IntEventConstraint(Event event, ConstraintType type) {
        super(type);
        this.event = event;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getN() {
        return this.n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

}
