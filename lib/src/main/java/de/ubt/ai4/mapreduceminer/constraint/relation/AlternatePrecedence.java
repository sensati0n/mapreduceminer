package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxilaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AlternatePrecedence extends DoubleEventConstraint implements Eventbased {

    public AlternatePrecedence(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    @Override
    public boolean logic(AuxilaryDatabase ad) {
        Event filteredSPEventB = super.getEventB();
        Event filteredMPEventB = super.getEventB();
        Event filteredEventA = super.getEventA();
        Event filteredSPEventA = super.getEventA();
        switch(super.getType())
        {
            case ACTIVATION:
                filteredSPEventB = filteredSPEventB.filter(super.getEventIdentifier());
                filteredMPEventB = filteredMPEventB.filter(super.getEventIdentifier(),  super.getAdditionalAttribute());
                filteredEventA = filteredEventA.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                filteredSPEventA = filteredSPEventA.filter(super.getEventIdentifier());
                break;
            case TARGET:
                filteredMPEventB = filteredMPEventB.filter(super.getEventIdentifier());
                filteredSPEventB = filteredSPEventB.filter(super.getEventIdentifier(),  super.getAdditionalAttribute());
                filteredSPEventA = filteredEventA.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                break;
            case CORRELATION:
        }


        if (!ad.breakAlternatePrecedence) {
            if(filteredSPEventA.equals(filteredSPEventB)) {
                ad.breakAlternatePrecedence = true;
            }
            if(!ad.tasksAlternatePrecedence.contains(filteredMPEventB)) {
                ad.tasksAlternatePrecedence.add(filteredMPEventB);
                return true;
            }
        }
        return false;
    }

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        return null;
    }
}
