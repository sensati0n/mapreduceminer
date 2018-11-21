package de.ubt.ai4.mapreduceminer.constraint.existence;

import de.ubt.ai4.mapreduceminer.Database;
import de.ubt.ai4.mapreduceminer.constraint.SingleEventConstraint;
import de.ubt.ai4.mapreduceminer.constraint.Tracebased;
import de.ubt.ai4.mapreduceminer.model.Event;
import de.ubt.ai4.mapreduceminer.result.ResultElement;
import de.ubt.ai4.mapreduceminer.util.AuxiliaryDatabase;
import de.ubt.ai4.mapreduceminer.util.ConstraintType;

import java.util.ArrayList;
import java.util.List;

public class Participation extends SingleEventConstraint implements Tracebased {

    public Participation() {

    }

    public Participation(Event event, ConstraintType type) {
        super(event, type);

    }

    @Override
    public boolean logic(AuxiliaryDatabase ad, int position, int size) {
        
        if(ad.eventCounter.get(super.getEvent())>=(1))
            return true;
        return false;
}

    @Override
    public ResultElement getResult(Database db, double sigma, int logSize) {
        double eta = db.getEta().get(getEvent());
        double support = sigma / logSize;

        int currentEpsilon = db.getEpsilon().get(getEvent());
        System.out.println("eps(" + super.getEvent() + ")" + currentEpsilon);
        double confidence = support * (currentEpsilon / (double) logSize);

        return new ResultElement(this.getClass().toString(), getEvent(),  support, confidence, this.getType());
    }
    
    @Override
    public int hashCode() {
        return 3^this.getEvent().hashCode() * 5^this.getType().hashCode() ;
    }

    @Override
    public boolean equals(Object o) {
       
        Participation other = (Participation) o;

        if(other.getEvent().equals(this.getEvent())) {
            if(other.getType().equals(this.getType()))
            {
                return true;
            }
        }

        return false;
    }
}
