package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

public class Precedence extends DoubleEventConstraint implements Eventbased {

    public Precedence(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    @Override
    public boolean logic(AuxilaryDatabase ad) {
        Event filteredEventB = super.getEventB();
        Event filteredEventA = super.getEventA();


        switch (super.getType()) {
            case ACTIVATION:
                filteredEventB = filteredEventB.filter(super.getEventIdentifier());
                filteredEventA = filteredEventA.filter(super.getEventIdentifier());

                break;
            case TARGET:
                filteredEventB = filteredEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                filteredEventA = filteredEventA.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                break;
            case CORRELATION:
        }

        if (!ad.breakPrecedence) {
            if (filteredEventA.equals(filteredEventB))
                ad.breakPrecedence = true;

            return true;
        }
        return false;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        return null;
    }
}
