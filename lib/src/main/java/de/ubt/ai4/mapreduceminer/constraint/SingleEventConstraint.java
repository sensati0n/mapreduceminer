package de.ubt.ai4.mapreduceminer.constraint;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

public abstract class SingleEventConstraint extends ConstraintImpl {

    private Event event;

    public SingleEventConstraint() {

    }

    public SingleEventConstraint(Event event, ConstraintType type) {
        super(type);
        this.event = event;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

 
}
