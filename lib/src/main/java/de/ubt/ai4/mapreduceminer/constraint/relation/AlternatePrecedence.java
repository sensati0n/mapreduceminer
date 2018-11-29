package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.constraint.HistoryBased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AlternatePrecedence extends DoubleEventConstraint implements Eventbased, HistoryBased {

    public AlternatePrecedence(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public AlternatePrecedence() {}

    @Override
    public boolean logic(AuxiliaryDatabase ad) {

        if(ad.currentJ < ad.currentI+1)
            return false;

        Event filteredSPEventB = super.getEventB();
        Event filteredMPEventB = super.getEventB();
        Event filteredEventA = super.getEventA();
        Event filteredSPEventA = super.getEventA();
        switch(super.getType())
        {
            case TARGET: // ACTIVATION IS HERE TARGET
                filteredMPEventB = filteredMPEventB.filter(super.getEventIdentifier()); //MP IS HERE SP!!
                filteredSPEventB = filteredSPEventB.filter(super.getEventIdentifier(),  super.getAdditionalAttribute());
                filteredSPEventA = filteredEventA.filter(super.getEventIdentifier(),  super.getAdditionalAttribute());
                break;
            case ACTIVATION:  //ACTIVTION
                filteredMPEventB = filteredMPEventB.filter(super.getEventIdentifier(),  super.getAdditionalAttribute());
                filteredSPEventB = filteredSPEventB.filter(super.getEventIdentifier());
                filteredSPEventA = filteredEventA.filter(super.getEventIdentifier());
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
        double eta = db.getEta().get(getEventB());
        double support = sigma / eta;

        int currentEpsilon = db.getEpsilon().get(getEventB());
        double confidence = support * (currentEpsilon / (double) logSize);

     
        return new ResultElement(this.getClass().toString(), getEventA(), getEventB(), support, confidence, this.getType());
    }

    @Override
    public int hashCode() {
        return 3^this.getEventA().hashCode() * 5^this.getEventB().hashCode() * 7^this.getType().hashCode() ;
    }

    @Override
    public boolean equals(Object o) {
       
        AlternatePrecedence other = (AlternatePrecedence) o;

        if(other.getEventA().equals(this.getEventA())) {
            if(other.getEventB().equals(this.getEventB())) {
                if(other.getType().equals(this.getType()))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
