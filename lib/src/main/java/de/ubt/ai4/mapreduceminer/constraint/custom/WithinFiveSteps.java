package de.ubt.ai4.mapreduceminer.constraint.custom;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.DoubleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Eventbased;
import de.ubt.ai4.mapreduceminer.constraint.FutureBased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;
import de.ubt.ai4.mapreduceminer.util.CustomAuxiliaryDatabase;

public class WithinFiveSteps extends DoubleEventConstraint implements Eventbased, FutureBased {

    public WithinFiveSteps(Event eventA, Event eventB, ConstraintType type) {
        super(eventA, eventB, type);
    }

    public WithinFiveSteps() {
        
    }


    @Override
	public boolean logic(AuxiliaryDatabase ad) {

        CustomAuxiliaryDatabase cad = (CustomAuxiliaryDatabase) ad;
        if(cad.currentJ < cad.currentI+1)
            return false;

        if(cad.currentJ > cad.currentI+5)
            return false;
            
        Event filteredEventB = super.getEventB();
        switch (super.getType()) {
            case ACTIVATION:
                filteredEventB  = filteredEventB.filter(super.getEventIdentifier());
                break;
            case TARGET:
                filteredEventB = filteredEventB.filter(super.getEventIdentifier(), super.getAdditionalAttribute());
                break;
            case CORRELATION:
        }

        if (!cad.tasksWithinFiveSteps.contains(filteredEventB)) {
            cad.tasksWithinFiveSteps.add(filteredEventB);
            {System.out.println("true");
            return true;}
        } else
            return false;
        }


    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        double eta = db.getEta().get(getEventA());
        double support = sigma / eta;

        int currentEpsilon = db.getEpsilon().get(getEventA());
        double confidence = support * (currentEpsilon / (double) logSize);

        //System.out.println("Support(" + constraint.getName() + currentEntry.getKey() + ") = \t\t" + support);
        //System.out.println("Confidence(" + currentEntry.getKey() + ") = \t" + confidence);
        return new ResultElement(this.getClass().toString(), getEventA(), getEventB(), support, confidence, this.getType());
    }

    @Override
    public int hashCode() {
        return 3^this.getEventA().hashCode() * 5^this.getEventB().hashCode() * 7^this.getType().hashCode() ;
    }

    @Override
    public boolean equals(Object o) {
       
        WithinFiveSteps other = (WithinFiveSteps) o;

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