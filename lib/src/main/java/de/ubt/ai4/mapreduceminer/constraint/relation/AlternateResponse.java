package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;


public class AlternateResponse extends DoubleEventConstraint implements Eventbased {

    public AlternateResponse(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }



    @Override
    public boolean logic(AuxilaryDatabase ad) {
        Event filteredSPEventB = super.getEventB();
        Event filteredMPEventB = super.getEventB();
        Event filteredEventA = super.getEventA();

        switch(super.getType())
        {
            case ACTIVATION:
                filteredSPEventB = filteredSPEventB.filter(super.getEventIdentifier());
                filteredMPEventB = filteredMPEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                filteredEventA = filteredEventA.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                break;
            case TARGET:
                filteredSPEventB = filteredSPEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                filteredMPEventB = filteredMPEventB.filter(super.getEventIdentifier());
                filteredEventA = filteredEventA.filter(super.getEventIdentifier());
                break;
            case CORRELATION:
        }

        if (!ad.breakAlternateResponse) {
            if(filteredEventA.equals(filteredMPEventB)) {
                ad.breakAlternateResponse = true;
            }
            if(!ad.tasksAlternateResponse.contains(filteredSPEventB)) {
                ad.tasksAlternateResponse.add(filteredSPEventB);
                return true;
            }

            return false;

        }
        else
            return false;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        return null;
    }
}
