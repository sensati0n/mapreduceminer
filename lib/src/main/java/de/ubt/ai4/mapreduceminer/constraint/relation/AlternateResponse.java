package de.ubt.ai4.mapreduceminer.constraint.relation;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.constraint.ConstraintImpl;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;


public class AlternateResponse extends DoubleEventConstraint implements Eventbased {

    public AlternateResponse(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public AlternateResponse() {}


    @Override
    public boolean logic(AuxiliaryDatabase ad) {

        if(ad.currentJ < ad.currentI+1)
            return false;

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
        double eta = db.getEta().get(getEventA());
        double support = sigma / eta;

        int currentEpsilon = db.getEpsilon().get(getEventA());
        double confidence = support * (currentEpsilon / (double) logSize);

        return new ResultElement(this.getClass().toString(), getEventA(), getEventB(), support, confidence, this.getType());    
    }

    @Override
    public int hashCode() {
        return 3^this.getEventA().hashCode() * 5^this.getEventB().hashCode() * 7^this.getType().hashCode() ;
    }

    @Override
    public boolean equals(Object o) {
       
        AlternateResponse other = (AlternateResponse) o;

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
