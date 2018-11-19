package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

public class ChainResponse extends DoubleEventConstraint implements Eventbased {

    public ChainResponse(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    @Override
    public boolean logic(AuxilaryDatabase ad) {
        Event filteredEventB = super.getEventB();
        switch(super.getType())
        {
            case ACTIVATION:
                filteredEventB = filteredEventB.filter(super.getEventIdentifier());
                break;
            case TARGET:
                filteredEventB = filteredEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                break;
            case CORRELATION:
        }

        if (!ad.tasksResponse.contains(filteredEventB)) {
            ad.tasksResponse.add(filteredEventB);
            return true;
        }
        else
            return false;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        return null;
    }
}
